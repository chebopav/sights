function checkParams() {
    var country = $('#country').val();
    var city = $('#city').val();
    var place = $('#place').val();

    if(country.length != 0 && city.length != 0 && place.length != 0) {
        $('#submit').removeAttr('disabled');
    } else {
        $('#submit').attr('disabled', 'disabled');
    }
}