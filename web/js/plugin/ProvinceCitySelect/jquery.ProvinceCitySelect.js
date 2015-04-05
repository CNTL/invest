/* province - city select 省-市选择插件
 *
 * NUGET: END LICENSE TEXT */

(function ($) {
    $.fn.ProCitySel = function(options){
        var defaults = {
            placeholder:"<i></i>",
            width: "30px",
            height: "30px",
            mode:"0",//0为单选，1为多选
            data:null,
            align:"left right bottom top",//left right bottom top
            hoverDuring: 300,
            outDuring: 300,
            callbackEvent: function (names,ids) {
                $.noop();
            }
        };
        var options = $.extend(defaults, options || {});

        var mul = "";
        if(options.mode=="1"){
            mul = 
              + '<div class="pc-sel-container">'
              + '   <div class="l">已选</div>'
              + '   <div class="c"></div>'
              + '   <div class="r">'
              + '       <div class="empty"></div>'
              + '       <div class="sure" id="citySure">确定</div>'
              + '       <div class="maxtip" >'
              + '            <div class="content">您的选择已达上限（5项）<br />请点击“确定”，或移除部分选项</div>'
              + '               <s><e></e></s>'
              + '           </div>'
              + '       </div>'
              + '  </div>'
              + '</div>';
        }


        var msghtml
              = options.placeholder
              + '<div style="display:none;left:0;" class="pc-container">'
              + '<table class="pc-table">'
              + mul
              + '<tbody>'
              + '  <tr>'
              + '     <td>'
              + '        <ul class="pc-city-list">'
              /*
              + '  <li>'
              + '                          <p>'
              + '                              <font><a title=北京市 href="javascript:;" pid="1" rcoid="1">北京市</a><i></i></font>'
              + '                         </p>'
              + '                         <div class="pc-citys" style="display: none">'
              + '                            <a href="javascript:;" p="qb">不限</a>'
              + '                            <a title=东城区 href="javascript:;" pid="1.35" rcoid="35">东城区</a>'
              + '                      </div>'
              + '                  </li>'
              */

              + '         </ul>'
              + '     </td>'
              + '  </tr>'
              + '</tbody>'
                + '</table>'
         + '</div>'
        ;
        function initData(jqobj) {
            if (!options.data) {
                return;
            }
            var sb = [];
            //先加载第一层
            $.each(options.data.citys, function (i, n) {
                if (n.pid == n.id) {
                    sb.push("<li>");
                    sb.push("<p>");
                    sb.push("<font><a title=\"" + n.name + "\" href=\"javascript:;\" pid=\"" + n.pid + "\" id=\"" + n.id + "\">" + n.name + "</a></font>");
                    sb.push("</p>");
                    //第一层省
                    sb.push("<div id=\"city_container_" + n.id + "\" class=\"pc-citys\" style=\"display: none\">");
                    sb.push("<a href=\"javascript:;\" p=\"qb\">不限</a>");
                    sb.push("</div>");
                    sb.push("</li>");
                }
               
            });
            jqobj.find(".pc-city-list").append(sb.join(""));
            //先加载第二层
            $.each(options.data.citys, function (i, n) {
                if (n.pid != n.id) {
                    var pid = n.pid.toString().split(".")[0];
                    var city_container = $("#city_container_" + pid.toString());
                    if (city_container.length > 0) {
                        city_container.append("<a title=\"" + n.name + "\" href=\"javascript:;\" pid=\"" + n.pid + "\" id=\"" + n.id + "\">" + n.name + "</a>");
                    }
                }
            });
        }
        function init() {

            $(".pc-city-list li").each(function () {
                if ($(this).find('.pc-citys').length <= 0) {
                    $(this).find('font').css("background", "none");
                }
            });

            /* 地区列表点击显示到已选 */
            $(".pc-container li p a").unbind().live('click', function () {
                if(options.mode=="1"){
                    // 判断选择的数量是否超出
                    if ($(".pc-container .selectedcolor").length >= 5) {
                        $(".pc-sel-container").find(".maxtip").show(0).delay(3000).fadeOut("slow");
                    } else {
                        $(this).addClass('selectedcolor');
                        copyCityItem(); // 将地区已选的拷贝
                    }
                }
                else{
                     
                     options.callbackEvent($(this).text(), $(this).attr("pid"));
                }
              
            });
            $(".pc-container .pc-citys a").unbind().live('click', function () {
                 if(options.mode=="1"){
                    // 判断选择的数量是否超出
                    if ($(".pc-container .selectedcolor").length >= 5) {
                        $(".pc-sel-container").find(".maxtip").show(0).delay(3000).fadeOut("slow");
                    } else {
                        if ($(this).attr("p") == "qb") {
                            $(this).parent().prev().find('font a').addClass('selectedcolor');
                            $(this).parent().find('a').removeClass('selectedcolor');
                        } else {
                            $(this).parent().prev().find('font a').removeClass('selectedcolor');
                            $(this).addClass('selectedcolor');
                        }
                        copyCityItem(); // 将地区已选的拷贝
                    }
                }
                else{
                    
                     if ($(this).attr("p") == "qb") {
                            var a_city = $(this).parent().prev().find('font a');
                            a_city.addClass('selectedcolor');
                            $(this).parent().find('a').removeClass('selectedcolor');
                            
                            options.callbackEvent(a_city.text(), a_city.attr("pid"));
                        } else {
                            
                             options.callbackEvent($(this).text(), $(this).attr("pid"));
                        }
                }
            });
           if(options.mode=="1"){
                // 地区确定选择
                $(".pc-sel-container").find(".sure").unbind().click(function () {
                    var a_cn = new Array();
                    var a_id = new Array();
                    var cityAcq = $(".pc-sel-container").find(".c");
                    cityAcq.find("a").each(function (index) {
                        // 如果选择的是一级地区将第二个参数补 0
                        var chid = new Array();
                        if ($(this).attr('pid')) {
                            chid = $(this).attr('pid').split(".");
                            if (chid.length < 2) {
                                chid.push(0);
                            }
                        }
                        var checkID = chid.join(".");
                        var checkText = $(this).attr('title');
                        a_id[index] = checkID;
                        a_cn[index] = checkText;
                    });
                    if (a_cn.length > 0) {
                        options.callbackEvent(a_cn, a_id);
                    } else {
                    }
                    $(".pc-container").hide();
                });
            }
        }
      
        function hoverDelayAll(jqobj) {
            var hoverTimer, outTimer;
            jqobj.hover(function (e) {
                clearTimeout(outTimer);
                hoverTimer = setTimeout(function () {
                    var pc_container = $(".pc-container");
                    setPosition()
                    //getPosition(e)
                    pc_container.show();
                    var dx = pc_container.offset().left; // 获取弹出框的x坐标
                    var dwidth = pc_container.outerWidth(true); // 获取弹出框的宽度
                    var lastx = dx + dwidth; // 加上弹出框的宽度获取弹出框最右边的x坐标
                    pc_container.find("li").each(function (index, el) {
                        var that = $(this);
                        var sx = that.offset().left; // 获取当前li的x坐标
                        that.hover(function () {
                            clearTimeout(outTimer);
                            hoverTimer = setTimeout(function () {
                                if (that.find('.pc-citys').length > 0) {
                                    that.addClass('selected');
                                    var tharsub = that.find('.pc-citys');
                                    var thap = that.find('p');
                                    thap.css("border-bottom", "0px");
                                    var swidth = tharsub.outerWidth(true); // 获取三级弹出框的宽度
                                    if ((lastx - sx) < swidth) { // 判断li与弹出框最右边的距离是否大于三级弹出框的宽度
                                        tharsub.css("left", -265); // 如果小于就改变三级弹出框x方向的位置
                                    }
                                    tharsub.show();
                                } else {
                                    that.find('a').css("color", "#f77d40");
                                }
                            }, options.hoverDuring);
                        }, function () {
                            clearTimeout(hoverTimer);
                            outTimer = setTimeout(function () {
                                if (that.find('.pc-citys').length > 0) {
                                    that.removeClass('selected');
                                    that.find('.pc-citys').hide();
                                } else {
                                    that.find('a').css("color", "#0180cf");
                                }
                            }, options.outDuring);
                        });
                    });
                    
                }, options.hoverDuring);
            }, function () {
                clearTimeout(hoverTimer);
                outTimer = setTimeout(function () {
                    $(".pc-container").hide();
                }, options.outDuring);
            });
            

        }

       
        function setPosition(){
            var fixed = false;
            var $elem = $(".pc-box");
            var popup = $(".pc-container");
            var $window = $(window);
            var $document = $(document);
            var winWidth = $window.width();
            var winHeight = $window.height();
            var docLeft =  $document.scrollLeft();
            var docTop = $document.scrollTop();


            var popupWidth = popup.width();
            var popupHeight = popup.height();
            var width = $elem ? $elem.outerWidth() : 0;
            var height = $elem ? $elem.outerHeight() : 0;
            var offset = getOffSet($elem[0]);
            var x = offset.left;
            var y = offset.top;
            var left =  fixed ? x - docLeft : x;
            var top = fixed ? y - docTop : y;


            var minLeft = fixed ? 0 : docLeft;
            var minTop = fixed ? 0 : docTop;
            var maxLeft = minLeft + winWidth - popupWidth;
            var maxTop = minTop + winHeight - popupHeight;

             var css = {};
            var align = options.align.split(' ');
            var reverse = {top: 'bottom', bottom: 'top', left: 'right', right: 'left'};
            var name = {top: 'top', bottom: 'top', left: 'left', right: 'left'};

            var temp = [{
                top: top - popupHeight,
                bottom: top + height,
                left: left - popupWidth,
                right: left + width
            }, {
                top: top,
                bottom: top - popupHeight + height,
                left: left,
                right: left - popupWidth + width
            }];


            var center = {
                left: left + width / 2 - popupWidth / 2,
                top: top + height / 2 - popupHeight / 2
            };

            
            var range = {
                left: [minLeft, maxLeft],
                top: [minTop, maxTop]
            };


            // 超出可视区域重新适应位置
            $.each(align, function (i, val) {

                // 超出右或下边界：使用左或者上边对齐
                if (temp[i][val] > range[name[val]][1]) {
                    val = align[i] = reverse[val];
                }

                // 超出左或右边界：使用右或者下边对齐
                if (temp[i][val] < range[name[val]][0]) {
                    align[i] = reverse[val];
                }

            });


            // 一个参数的情况
            if (!align[1]) {
                name[align[1]] = name[align[0]] === 'left' ? 'top' : 'left';
                temp[1][align[1]] = center[name[align[1]]];
            }

            css[name[align[0]]] = parseInt(temp[0][align[0]]);
            css[name[align[1]]] = parseInt(temp[1][align[1]]);
            popup.css(css);

            

        }

        function getOffSet(anchor){
            var isNode = anchor.parentNode;
            var offset = isNode ? $(anchor).offset() : {
                left: anchor.pageX,
                top: anchor.pageY
            };


            anchor = isNode ? anchor : anchor.target;
            var ownerDocument = anchor.ownerDocument;
            var defaultView = ownerDocument.defaultView || ownerDocument.parentWindow;
            
            if (defaultView == window) {// IE <= 8 只能使用两个等于号
                return offset;
            }

            // {Element: Ifarme}
            var frameElement = defaultView.frameElement;
            var $ownerDocument = $(ownerDocument);
            var docLeft =  $ownerDocument.scrollLeft();
            var docTop = $ownerDocument.scrollTop();
            var frameOffset = $(frameElement).offset();
            var frameLeft = frameOffset.left;
            var frameTop = frameOffset.top;
            
            return {
                left: offset.left + frameLeft - docLeft,
                top: offset.top + frameTop - docTop
            };
        }
         
        function copyCityItem() {
            var cityAcq = $(".pc-sel-container").find(".c");
            var cityacqhtm = '';
            $(".pc-container .selectedcolor").each(function () {
                cityacqhtm += '<a pid="' + $(this).attr('pid') + '" href="javascript:;" title="' + $(this).attr('title') + '"><div class="text">' + $(this).attr('title') + '</div><div title="移除" class="close">×</div></a>';
            });
            cityAcq.html(cityacqhtm);
            // 已选项目绑定点击事件
            cityAcq.find("a").unbind().click(function () {
                var selval = $(this).attr('title');
                $(".pc-container .selectedcolor").each(function () {
                    if ($(this).attr('title') == selval) {
                        $(this).removeClass('selectedcolor');
                        copyCityItem();
                    }
                });
            });
            // 清空
            $(".pc-sel-container").find(".empty").unbind().click(function () {
                cityAcq.empty();
                $(".pc-container .selectedcolor").each(function () {
                    $(this).removeClass('selectedcolor');
                });
            });
        }

        return $(this).each(function () {
            $(this).addClass("pc-box");
            $(this).css({ "width": options.width, "height": options.height });
            $(this).append(msghtml);
            initData($(this));
            init();
            hoverDelayAll($(this));
        });
    }      
})(jQuery);