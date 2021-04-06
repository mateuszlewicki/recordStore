var search_box = $(".search_box");
var search_btn = $( "#search-btn" );
var menu_box = $('.menu-main');
var menu_btn = $('.menu-toggle-btn');

function toggleSearch() {
    search_box.toggleClass('active');
    search_btn.toggleClass('active');
}
search_btn.click(function(e) {
    menu_box.removeClass('active');
    menu_btn.removeClass('active');
    e.preventDefault();
    toggleSearch();
});

menu_btn.click(function (e){
    search_box.removeClass('active');
    search_btn.removeClass('active');
    e.preventDefault();
    toggleMenu();
});

function toggleMenu() {
    menu_box.toggleClass('active');
    menu_btn.toggleClass('active');
}

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