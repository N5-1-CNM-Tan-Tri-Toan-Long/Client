// JavaScript for label effects only
$(window).on("load",function(){
    $(".col-3-diem input").val("");

    $(".input-effect input").focusout(function(){
        if($(this).val() != ""){
            $(this).addClass("has-content");
        }else{
            $(this).removeClass("has-content");
        }
    })
});