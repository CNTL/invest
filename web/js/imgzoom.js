(function($){

    $.fn.zoom = function(settings){
                
                settings = $.extend({
                    height:0,
                    width:0,
                    loading:"../img/wait.gif"
                    },settings);
                    
                var images = this;
                $(images).hide();
                var loadding = new Image();
                loadding.className="loadding"
                loadding.src = settings.loading;
                $(images).after(loadding);    
                
              
                var preLoad = function($this){
                    var img = new Image();
                    img.src = $this.src;
                    if (img.complete) { 
                        processImg.call($this);
                        return;
                    }

                    img.onload = function(){
                       
                        processImg.call($this);
                        img.onload=function(){};
                    } 
                }

                function processImg(){
                 
                        var m = this.height-settings.height;
                        var n = this.width - settings.width;
                        if(m>n)                        
                            this.height = this.height>settings.height ? settings.height : this.height;
                        else
                            this.width = this.width >settings.width ? settings.width : this.width;

                        $(this).next(".loadding").remove()
                        $(this).show();
                }
                
                return $(images).each(function(){
                    preLoad(this);
                });                
        }

})(jQuery);
