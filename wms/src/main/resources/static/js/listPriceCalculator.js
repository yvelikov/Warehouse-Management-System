$(document).ready(function () {

    $("input").bind('keyup change click',multInputs);

    function multInputs() {
        let listPrice = 0;
        $("div.price-input").each(function () {
            let $val1 = $('#deliveryPrice', this).val();
            let $val2 = $('#markUp', this).val();
            listPrice = $val1 * (1 + $val2 / 100);
        });

        $("#listPrice").val(listPrice.toFixed(2));
    }
});