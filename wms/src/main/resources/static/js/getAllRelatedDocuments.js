$(document).ready(function () {

    let partnerId = $('#partnerId', this).text();

    const url = '/documents/partner?partnerId=' + partnerId;

    $("#docDetailsBtn").one('click', function () {
        fetch(url, {
            credentials: "same-origin",
            method: "GET",
            redirect: "manual"
        }).then(response => {
            if (response.ok) {
                response.json().then(function (data) {
                    fillTable(data);
                }).catch(function (err) {
                    console.log('Fetch error - ', err);
                });
            } else {
                console.warn('It seems there was a problem with your request. Status Code: ' +
                    response.status);
            }
        });

    });

    function fillTable(data) {
        let i = 1;
        $("#docTable").fadeToggle("slide");
        for (let elem of data['content']) {
            $('#docs-body').append('<tr >' +
                '<td>' + i + '</td>' +
                '<td>' + elem['documentCode'] + '</td>' +
                '<td>' + elem['date'] + '</td>' +
                '<td>' + elem['type'] + '</td>' +
                '<td class="text-center">' + '<a class="btn btn-outline-info btn-sm" href="/documents/delivery_notes/details/' + elem['id'] + '"><i class="fas fa-info-circle"></i>\n' +
                '<small>Details</small></a>' + '</td>' +
                '</tr>'
            );
            i++;
        }

        if (data['totalElements'] === 0){
            $('#docs-body').append('<tr><td colspan="5">No related documents for this partner.</td></tr>');
        }
    }
});


