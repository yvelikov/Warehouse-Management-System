$("#search").on("change keyup", function() {
    let value = $.trim($(this).val());
    let regex = new RegExp(value, "gi");

    $("table tr").each(function(index) {

        if (index !== 0) {

            $row = $(this);

            let id = $row.find("td").text();

            if (id.match(regex) !== null){
                $row.show();
            }
            else {
                $row.hide();
            }
        }
    });
});

//<input type="text" id="search" placeholder="  live search"></input>