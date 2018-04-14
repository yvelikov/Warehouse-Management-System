
$(document).ready(function() {

    let constants = {
        serviceUrl: "http://127.0.0.1:8000"
    };

    $(function () {
        let token = $("meta[name='_csrf']").attr("content");
        let header = $("meta[name='_csrf_header']").attr("content");
        $(document).ajaxSend(function(e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });
    });

    $("#get-suppliers").click(function() {
        let i = 1;

        // const url = constants.serviceUrl + '/parts/deliver/partsBySupplier?id='+'4e098697-5a83-474c-896d-c396c1dd8664';

        $.ajax({

            type: 'GET',
            url: constants.serviceUrl + '/partners/suppliers',
            headers: {
                'Content-Type':'application/x-www-form-urlencoded'
            }

        }).done((data) =>{
            console.log(data)
            // for (let elem of data){
            //     $('.all-suppliers')
            //         .append('<tr class="row">'
            //             + '<td class="col-md-1" scope="col"><h5>' + i + '</h5></td>'
            //             + '<td class="col-md-3" scope="col"><h5>' + elem['name'] + '</h5></td>'
            //             + '<td class="col-md-3" scope="col"><h5>$' + elem['vatNumber'] + '</h5></td>'
            //             + '</tr>');
            //
            //     i++;
            // }
        }).fail((err)=>{
            console.log(err)
        });
    });
});


