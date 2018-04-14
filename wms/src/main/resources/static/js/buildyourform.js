
$(document).ready(function() {

    let constants = {
        serviceUrl: "http://127.0.0.1:8000"
    };

    $("#add").click(function() {
        let lastField = $("#buildyourform div:last");
        let intId = (lastField && lastField.length && lastField.data("idx") + 1) || 1;
        let fieldWrapper = $("<div class=\"fieldwrapper\" id=\"field" + intId + "\"/>");
        fieldWrapper.data("idx", intId);
        let fName = $("<input type=\"text\" class=\"fieldname\" />");
        let fType = $("<select id=\"parts-dropdown\" name=\"parts\"></select>");



        let removeButton = $("<button type=\"button\" class=\"btn btn-danger btn-sm remove\"><i class=\"fas fa-minus-circle\"></i></button>");
        removeButton.click(function() {
            $(this).parent().remove();
        });
        fieldWrapper.append(fName);
        fieldWrapper.append(fType);

        let dropdown = $('#parts-dropdown');

        dropdown.empty();

        dropdown.append('<option selected="true" disabled>Select part</option>');
        dropdown.prop('selectedIndex', 0);

        // const url = constants.serviceUrl + '/parts/deliver/partsBySupplier?id='+'4e098697-5a83-474c-896d-c396c1dd8664';

        $.ajax({
            type: 'GET',
            url: constants.serviceUrl + '/parts/deliver/partsBySupplier?id='+'4e098697-5a83-474c-896d-c396c1dd8664'
        }).done((data) =>{
            for (let elem of data){
                $('#parts-dropdown').append($('<option></option>').attr('value', elem.id).text(entry.name))
            }
        }).fail((err)=>{
            console.log(err)
        });

        fieldWrapper.append(removeButton);
        $("#buildyourform").append(fieldWrapper);
    });
});


