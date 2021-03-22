var search_box = $(".search_box");
var search_btn = $( "#search-btn" );
function closeSearch() {
    search_box.toggleClass('active');
    search_btn.toggleClass('active');
}
$( "#search-btn" ).click(function(e) {
    e.preventDefault();
    closeSearch();
});

$(function() {
    $('#search').autocomplete({
        source: 'autocomplete',
        minLength: 2
    });
});

function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            $("#image").attr("src", e.target.result);
        }
        reader.readAsDataURL(input.files[0]);
    }
}

$("#data").change(function () {
    readURL(this);
})