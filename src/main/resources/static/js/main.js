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