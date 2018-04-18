$(document).ready(function () {

    let docId = $('#docId', this).val();
    let docType =$('#docType', this).val();

    const deliveryUrl = '/operation/part_delivery?documentId=' + docId;
    const issueUrl = '/operation/part_delivery?documentId=' + docId;

    switch (docType){
        case "delivery note":
            fetch(deliveryUrl, {
                credentials: "same-origin",
                method: "GET",
                redirect: "manual"
            }).then(response => {
                if (response.ok) {
                    response.json().then(function (data) {
                        console.log(data);
                    }).catch(function (err) {
                        console.log('Fetch error - ', err);
                    });
                } else {
                    console.warn('It seems there was a problem with your request. Status Code: ' +
                        response.status);
                }
            });
            break;
        case "issue note":
            fetch(issueUrl, {
                credentials: "same-origin",
                method: "GET",
                redirect: "manual"
            }).then(response => {
                if (response.ok) {
                    response.json().then(function (data) {
                        console.log(data);
                    }).catch(function (err) {
                        console.log('Fetch error - ', err);
                    });
                } else {
                    console.warn('It seems there was a problem with your request. Status Code: ' +
                        response.status);
                }
            });
            break
    }
});


