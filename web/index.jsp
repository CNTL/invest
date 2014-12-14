<%@ include file="./include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="./inc/meta.inc"%>
	<script type="text/javascript" src="static/js/idangerous.swiper.min.js"></script>
	<script type="text/javascript" src="js/utils.js"></script>
	<script type="text/javascript">
		var webroot = "<c:out value="${rootPath}"/>";
		$(function () {
			setCookie("loginCurrentUrl", window.location.href);
			setCookie("loginCurrentMenu", "1");
            var mySwiper = new Swiper('.swiper', {
                pagination: '.pagination',
                loop: true,
                wrapperClass: 'wrapper',
                slideClass: 'slide',
                grabCursor: true,
                paginationClickable: true
            });
            $('.left').on('click', function (e) {
                e.preventDefault();
                mySwiper.swipePrev();
            });
            $('.right').on('click', function (e) {
                e.preventDefault();
                mySwiper.swipeNext();
            });
        });
	</script>
	<style type="text/css">
		.container .cell{margin-right:0px;}
	</style>
</head>
<body>
	<%@include file="./inc/header.inc"%>
	
	<div class="scroll">
        <a class="left" href="#"></a>
        <a class="right" href="#"></a>
        <div class="swiper">
            <div class="wrapper">
                <div class="slide"> <img src="static/image/temp/pic1.png" /> </div>
                <div class="slide"> <img src="static/image/temp/pic1.png" /> </div>
                <div class="slide"> <img src="static/image/temp/pic1.png" /> </div>
            </div>
        </div>
        <div class="pagination"></div>
    </div>
	
	<div class="indextip">
        <div class="wrap">
            <h2>何为合众映画？</h2>
            映画，亦称电影；合众，即指合众人之力。我们是中国第一个全方位专业的电影服务型网站，年轻的电影人可以在这里发起项目，众筹融资。寻找机遇，展示自我。我们是一个为电影而生的机会平台。
        </div>
    </div>
	<div class="container">
		<div class="cell">
			<div class="box">
				<div class="box_top"></div>
				<div class="box_main project">
					<div class="pic">
						<a href="#"><img src="static/image/temp/pic2.png" /></a>
						<span>项目</span>
					</div>
					<div>
						<div class="title">
							<a href="#">李东方纪录片《童年》</a>
						</div>
						<div class="desc">
							关注中国留守儿童生存状态,中国留守儿童5800万生存状态。需关注改编自徐皓峰的原著小说，小说从20年代到抗战前，讲述小道士何安下16岁修道下山的成长和习武经历，呈现太极与道、佛、儒等诸家的联系。徐皓峰是如今小有盛名的武侠小说家，也是《一代宗师》的编剧之一。
						</div>
						<div class="info">
							目标：40天 ￥80000
							<span>众筹中</span>
						</div>
                      <div class="progress">
							<div class="now" style="width: 75%;"></div>
						</div>
						<div class="status">
                          <ul>
								<li><span>75%</span><br />已达</li>
								<li><span>￥64500</span><br />已筹资</li>
								<li class="last"><span>10天</span><br />剩余时间</li>
							</ul>
                      </div>
					</div>
					<div class="tool">
						<a href="#" class="share">分享</a>
						<a href="#" class="view"></a>
					</div>
				</div>
			</div>
			<div class="box">
                <div class="box_top"></div>
                <div class="box_main job">
                    <div class="pic">
                        <a href="#"><img src="static/image/temp/pic2.png" /></a>
                        <span>影聘</span>
                    </div>
                    <div>
                        <div class="title">
                            <a href="#">摄影师</a>
                            <span>小马奔腾电影</span>
                        </div>
                        <div class="info">
                            <ul>
                                <li>100K</li>
                                <li>北京</li>
                                <li>3个月</li>
                            </ul>
                        </div>
                        <div class="desc">
                            <span>陈凯歌大戏《道士下山》招聘摄影助理名陈凯歌大戏《道士下山》招聘摄影助理10名</span><br />
                            发布时间：2014-09-20<br />
                            已投递简历人数：2人
                        </div>
                    </div>
                    <div class="tool">
                        <a href="#" class="share">分享</a>
                        <a href="#" class="view"></a>
                    </div>
                </div>
                <div class="box_bottom"></div>
            </div>
			<div class="box">
                <div class="box_top"></div>
                <div class="box_main people">
                    <div class="pic">
                        <img src="static/image/temp/pic2.png" />
                        <span>影人</span>
                    </div>
                    <div class="title">
                        <a href="#">常沁源</a>
                        <span>演员</span>
                    </div>
                    <div class="desc">
                        毕业于北京影视学院，作品《大宅门》《雨季中》等
                    </div>
                    <div class="tool">
                        <a href="#" class="share">分享</a>
                        <a href="#" class="view"></a>
                    </div>
                </div>
                <div class="box_bottom"></div>
            </div>
		</div>
		
		<div class="cell">

            <div class="box">
                <div class="box_top"></div>
                <div class="box_main project">
                    <div class="pic">
                        <a href="#"><img src="static/image/temp/pic2.png" /></a>
                        <span>项目</span>
                    </div>
                    <div>
                        <div class="title">
                            <a href="#">《地下城 The Tube》</a>
                        </div>
                        <div class="desc">
                            讲述城市地铁地下情
                        </div>
                        <div class="info">
                            目标：40天 ￥80000
                            <span>众筹中</span>
                        </div>
                        <div class="progress">
                            <div class="now" style="width: 50%;"></div>
                        </div>
                        <div class="status">
                            <ul>
                                <li><span>50%</span><br />已达</li>
                                <li><span>￥40000</span><br />已筹资</li>
                                <li class="last"><span>10天</span><br />剩余时间</li>
                            </ul>
                        </div>
                    </div>
                    <div class="tool">
                        <a href="#" class="share">分享</a>
                        <a href="#" class="view"></a>
                    </div>
                </div>
                <div class="box_bottom"></div>
            </div>

            <div class="box">
                <div class="box_top"></div>
                <div class="box_main job">
                    <div class="pic">
                        <a href="#"><img src="static/image/temp/pic2.png" /></a>
                        <span>影聘</span>
                    </div>
                    <div>
                        <div class="title">
                            <a href="#">场记</a>
                            <span>华谊兄弟</span>
                        </div>
                        <div class="info">
                            <ul>
                                <li>100K</li>
                                <li>北京</li>
                                <li>3个月</li>
                            </ul>
                        </div>
                        <div class="desc">
                            <span>张艺媒大戏《归来》招聘导演助理1名</span><br />
                            发布时间：2014-09-20<br />
                            已投递简历人数：2人
                        </div>
                    </div>
                    <div class="tool">
                        <a href="#" class="share">分享</a>
                        <a href="#" class="view"></a>
                    </div>
                </div>
                <div class="box_bottom"></div>
            </div>

            <div class="box">
                <div class="box_top"></div>
                <div class="box_main people">
                    <div class="pic">
                        <img src="static/image/temp/pic2.png" />
                        <span>影人</span>
                    </div>
                    <div class="title">
                        <a href="#">张慧雯</a>
                        <span>演员</span>
                    </div>
                    <div class="desc">
                        毕业于北京影视学院，作品《东方日出》《和风日下》等
                    </div>
                    <div class="tool">
                        <a href="#" class="share">分享</a>
                        <a href="#" class="view"></a>
                    </div>
                </div>
                <div class="box_bottom"></div>
            </div>
        </div>
        <div class="cell">

            <div class="box">
                <div class="box_top"></div>
                <div class="box_main project">
                    <div class="pic">
                        <a href="#"><img src="static/image/temp/pic2.png" /></a>
                        <span>项目</span>
                    </div>
                    <div>
                        <div class="title">
                            <a href="#">《傻娘》感人！</a>
                        </div>
                        <div class="desc">
                            弱智母亲屡遭排挤十年该剧讲述了一个贫
                            困家庭户主石头收养一个傻妞的流浪女人
                            ，结婚生子后引发的故事。弱智母亲屡遭
                            排挤十年该剧讲述了一个贫困家庭户主石
                            头收养一个傻妞的流浪女人，结婚生子后
                            引发的故事。
                        </div>
                        <div class="info">
                            目标：40天 ￥10000
                            <span class="complete">众筹完成</span>
                        </div>
                        <div class="progress">
                            <div class="now complete" style="width: 100%;"></div>
                        </div>
                        <div class="status">
                            <ul>
                                <li><span>100%</span><br />已达</li>
                                <li><span>￥10000</span><br />已筹资</li>
                                <li class="last"><span>10天</span><br />剩余时间</li>
                            </ul>
                        </div>
                    </div>
                    <div class="tool">
                        <a href="#" class="share">分享</a>
                        <a href="#" class="view"></a>
                    </div>
                </div>
                <div class="box_bottom"></div>
            </div>

            <div class="box">
                <div class="box_top"></div>
                <div class="box_main job">
                    <div class="pic">
                        <a href="#"><img src="static/image/temp/pic2.png" /></a>
                        <span>影聘</span>
                    </div>
                    <div>
                        <div class="title">
                            <a href="#">导演</a>
                            <span>小马奔腾电影</span>
                        </div>
                        <div class="info">
                            <ul>
                                <li>100K</li>
                                <li>北京</li>
                                <li>3个月</li>
                            </ul>
                        </div>
                        <div class="desc">
                            <span>国外大戏《潘神的迷宫》招聘专业化妆师助理20名</span><br />
                            发布时间：2014-09-20<br />
                            已投递简历人数：2人
                        </div>
                    </div>
                    <div class="tool">
                        <a href="#" class="share">分享</a>
                        <a href="#" class="view"></a>
                    </div>
                </div>
                <div class="box_bottom"></div>
            </div>

            <div class="box">
                <div class="box_top"></div>
                <div class="box_main people">
                    <div class="pic">
                        <img src="static/image/temp/pic2.png" />
                        <span>影人</span>
                    </div>
                    <div class="title">
                        <a href="#">叶小凯</a>
                        <span>演员</span>
                    </div>
                    <div class="desc">
                        毕业于北京影视学院，作品《12星座》《功夫》等
                    </div>
                    <div class="tool">
                        <a href="#" class="share">分享</a>
                        <a href="#" class="view"></a>
                    </div>
                </div>
                <div class="box_bottom"></div>
            </div>
        </div>
        <div class="cell cell_last">
            <div class="box">
                <div class="box_top"></div>
                <div class="box_main">
                    <div class="notice">
                        <h2>最新通知</h2>
                        <div class="content">
                            <span class="i"></span>巴黎电影节短片项目征片于2014年12月12日开始，欢迎咨询报名。
                        </div>
                        <div class="more">
                            <a href="#">查看更多</a>
                        </div>
                    </div>
                </div>
                <div class="box_bottom"></div>
            </div>

            <div class="box">
                <div class="box_top"></div>
                <div class="box_main project">
                    <div class="pic">
                        <a href="#"><img src="static/image/temp/pic2.png" /></a>
                        <span>项目</span>
                    </div>
                    <div>
                        <div class="title">
                            <a href="#">《出道四年》</a>
                        </div>
                        <div class="desc">
                            所有人不过是一场过眼云烟
                        </div>
                        <div class="info">
                            目标：40天 ￥20000
                            <span class="complete">众筹完成</span>
                        </div>
                        <div class="progress">
                            <div class="now complete" style="width: 100%;"></div>
                        </div>
                        <div class="status">
                            <ul>
                                <li><span>100%</span><br />已达</li>
                                <li><span>￥20000</span><br />已筹资</li>
                                <li class="last"><span>10天</span><br />剩余时间</li>
                            </ul>
                        </div>
                    </div>
                    <div class="tool">
                        <a href="#" class="share">分享</a>
                        <a href="#" class="view"></a>
                    </div>
                </div>
                <div class="box_bottom"></div>
            </div>

            <div class="box">
                <div class="box_top"></div>
                <div class="box_main job">
                    <div class="pic">
                        <a href="#"><img src="static/image/temp/pic2.png" /></a>
                        <span>影聘</span>
                    </div>
                    <div>
                        <div class="title">
                            <a href="#">灯光</a>
                            <span>梦工厂电影</span>
                        </div>
                        <div class="info">
                            <ul>
                                <li>100K</li>
                                <li>北京</li>
                                <li>3个月</li>
                            </ul>
                        </div>
                        <div class="desc">
                            <span>陈凯歌大戏《道士下山》招聘造型师助理1名影片讲述的是民国时期，一个不堪忍受山中寂寞的小道士何安下偷偷下山，结果遭遇到一系列诡异奇幻的人物和事件，从而改变人生的故事。</span><br />
                            发布时间：2014-09-20<br />
                            已投递简历人数：2人
                        </div>
                    </div>
                    <div class="tool">
                        <a href="#" class="share">分享</a>
                        <a href="#" class="view"></a>
                    </div>
                </div>
                <div class="box_bottom"></div>
            </div>

            <div class="box">
                <div class="box_top"></div>
                <div class="box_main people">
                    <div class="pic">
                        <img src="static/image/temp/pic2.png" />
                        <span>影人</span>
                    </div>
                    <div class="title">
                        <a href="#">张暴</a>
                        <span>演员</span>
                    </div>
                    <div class="desc">
                        毕业于北京影视学院，作品《小兵张嘎》《我是传奇》等
                    </div>
                    <div class="tool">
                        <a href="#" class="share">分享</a>
                        <a href="#" class="view"></a>
                    </div>
                </div>
                <div class="box_bottom"></div>
            </div>
        </div>
        <div class="clear"></div>
	</div>
	
	<%@include file="../inc/footer.inc"%>
</body>
</html>