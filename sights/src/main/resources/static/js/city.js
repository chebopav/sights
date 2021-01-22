$(function(){
  $('#country').on('change', function(){
    let countryId =  $(this).val();
    $.ajax({
      type: "GET",
      dataType: 'JSON',
      url: '/citiesOfCountry/sel?id=' + countryId
    }).done(function(cities){
      let citiesOptions = '';
      cities.forEach((city) => {
        citiesOptions += `<option value="${city.id}">${city.name}</option>`;
      });
      $('#city').html(citiesOptions);
    }).fail(function() {
      alert("error");
    });
  });
})