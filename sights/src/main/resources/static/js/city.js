$(function(){
// Запускаем функцию при изменении страны
    alert('a');
    $('#country').on('change', function(){
      let countryId =  $(this).val();  // создаем переменную с выбранной страной - это Object из которого нам нужен будет value

      $.ajax({
        type: "GET",
        url: 'localhost:9999/cities/sel?id=' + countryId.value
      }).done(function(cities){
        var citiesArray = JSON.parse(cities);

        console.log( citiesArray );
      }).fail(function() {
        alert( "error" );
      });
    });
})