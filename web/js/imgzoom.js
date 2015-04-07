(function($){

    $.fn.zoom = function(settings){
                
                settings = $.extend({
                	mode:1, //1表示等宽缩放,2表示等高缩放3.等比居中缩放
                    height:232,
                    width:160,
                    overflow:"hidden",//在等宽或等高时，图片的div父容器内容溢出是否隐藏，这样显示的好看
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
                	 if( (parseInt(settings.width,10)<this.width) && (parseInt(settings.height,10)<this.height)){
                		//如果图片大小都大于容器大小
                 		var rateW = (parseInt(this.width,10)/parseInt(settings.width,10));
                 		var rateH = (parseInt(this.height,10)/parseInt(settings.height,10));
                 		if(settings.mode==3){//等比缩放
                 			
                 			if(rateW>=rateH){
                     			//取压缩比最大的
                 				$(this).css({"height":(this.height / rateW)+"px","width":(this.width / rateW)+"px"});
                     			
                     			if(rateW!=rateH){
                     				//居中显示图片
                     				var margin = ((parseInt(settings.height,10) - parseInt(this.height,10))/2);
                     				$(this).css({"margin-top":margin+"px"});
                     			}
                     		}
                     		else{
                     			//取压缩比最大的
                     			 
                     			$(this).css({"height":(this.height / rateH)+"px","width":(this.width / rateH)+"px"});
                     			//居中显示图片
                 				var margin = ((parseInt(settings.width,10) - parseInt(this.width,10))/2);
                 				$(this).css({"margin-left":margin+"px"});
                     		}
                 		}
                 		if(settings.mode==1){//等宽缩放,按照宽度的比率来缩放
                 			 
                 			$(this).css({"height":( this.height / rateW)+"px","width":(this.width / rateW)+"px"});
                 			if(settings.overflow.toString().length>0){
                 				//找到最近的div容器
                 				var div = $(this).closest("div");
                 				if(div.length>0){
                 					div.css({"overflow":settings.overflow});
                 				}
                 			}
                 		}
                 		
                 		if(settings.mode==2){//等高缩放,按照高度的比率来缩放
                 			 
                 			$(this).css({"height":( this.height / rateH)+"px","width":(this.width / rateH)+"px"});
                 			if(settings.overflow.toString().length>0){
                 				//找到最近的div容器
                 				var div = $(this).closest("div");
                 				if(div.length>0){
                 					div.css({"overflow":settings.overflow});
                 				}
                 			}
                 		}
                	 }
                	 else{
                		 //当图片大小宽或高有一方小于容器的大小时
                		 var disW = parseInt(this.width,10)-parseInt(settings.width,10);
                  		 var disH = parseInt(this.height,10)-parseInt(settings.height,10);
                  		$(this).css({"height":( this.height)+"px","width":(this.width )+"px"});
                		 if( disW<=0 && disH<=0){
                			 //两者都小于容器的大小,就居中显示
                			 var padding_v = (Math.abs(disH)/2); 
                			 var padding_h = (Math.abs(disW)/2); 
                			 $(this).css({
                				 "margin-top":padding_v+"px",
                				 "margin-left":padding_h+"px"
                			 });
                			 
                		 }else {
                			 //高度超出了或宽超出了，就隐藏
                			 if(settings.overflow.toString().length>0){
                  				//找到最近的div容器
                  				var div = $(this).closest("div");
                  				if(div.length>0){
                  					div.css({"overflow":settings.overflow});
                  				}
                  			}
                		 }
                	 }
//                        var m = this.height-settings.height;
//                        var n = this.width - settings.width;
//                        if(m>n)                        
//                            this.height = this.height>settings.height ? settings.height : this.height;
//                        else
//                            this.width = this.width >settings.width ? settings.width : this.width;

                        $(this).next(".loadding").remove()
                        $(this).show();
                }
                
                return $(images).each(function(){
                    preLoad(this);
                });                
        }

})(jQuery);
