$(document).ready(function () {

    let dropdown = document.getElementById('customers-dropdown');
    dropdown.length = 0;

    let defaultOption = document.createElement('option');
    defaultOption.text = 'Choose Customer';

    dropdown.add(defaultOption);
    dropdown.selectedIndex = 0;

    const url = '/partners/customers';

    fetch(url, {
        credentials: "same-origin",
        method: "GET",
        redirect: "manual"
    }).then(response => {
        if (response.ok) {
            response.json().then(function (data) {
                let option;
                for (let i = 0; i < data.length; i++) {
                    option = document.createElement('option');
                    option.text = data[i].vatNumber + ' - ' + data[i].name;
                    option.value = data[i].id;
                    dropdown.add(option);
                }

                $(function () {
                    $('#customers-dropdown').filterByText($('#customer-filter'));
                });

                jQuery.fn.filterByText = function (textbox) {
                    return this.each(function () {
                        let select = this;
                        let options = [];
                        $(select).find('option').each(function () {
                            options.push({
                                value: $(this).val(),
                                text: $(this).text()
                            });
                        });

                        $(select).data('options', options);

                        $(textbox).bind('change keyup', function () {
                            let options = $(select).empty().data('options');
                            let search = $.trim($(this).val());
                            let regex = new RegExp(search, "gi");

                            $.each(options, function (i) {
                                let option = options[i];
                                if (option.text.match(regex) !== null) {
                                    $(select).append(
                                        $('<option>').text(option.text).val(option.value)
                                    );
                                }
                            });
                        });
                    });
                };

            }).catch(function (err) {
                console.log('Fetch error - ', err);
            });
        } else {
            console.warn('It seems there was a problem with your request. Status Code: ' +
                response.status);
        }
    });

    $("#nextButton").click(function() {
            let customerId = $('#customers-dropdown').val();
            let numberOfRows = $('#numberOfRows').val();

            console.log(customerId);
            console.log(numberOfRows);

            fetch("/parts/issue/select_parts",{
                credentials: "same-origin",
                method: "POST",
                redirect: "manual",
                data: {
                    customerId: customerId,
                    numberOfRows: numberOfRows
                }
            });
    });
});


