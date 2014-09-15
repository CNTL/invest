<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title>上传照片</title>
    <script type="text/javascript" src="../ueditor/dialogs/internal.js"></script>

    <!-- jquery -->
    <script type="text/javascript" src="../ueditor/third-party/jquery-1.10.2.min.js"></script>

    <!-- webuploader -->
    <script src="../ueditor/third-party/webuploader/webuploader.min.js"></script>
    <link rel="stylesheet" type="text/css" href="../ueditor/third-party/webuploader/webuploader.css">

    <!-- image dialog -->
    <link rel="stylesheet" href="../ueditor/dialogs/image/image.css" type="text/css" />
</head>
<body>

    <div class="wrapper">
        <div class="alignBar">
            <label class="algnLabel"><var id="lang_input_align"></var></label>
                    <span id="alignIcon">
                        <span id="noneAlign" class="none-align focus" data-align="none"></span>
                        <span id="leftAlign" class="left-align" data-align="left"></span>
                        <span id="rightAlign" class="right-align" data-align="right"></span>
                        <span id="centerAlign" class="center-align" data-align="center"></span>
                    </span>
            <input id="align" name="align" type="hidden" value="none"/>
        </div>
        <div id="tabbody" class="tabbody">
            <!-- 上传图片 -->
            <div id="upload" class="panel focus">
                <div id="queueList" class="queueList">
                    <div class="statusBar element-invisible">
                        <div class="progress">
                            <span class="text">0%</span>
                            <span class="percentage"></span>
                        </div><div class="info"></div>
                        <div class="btns">
                            <div id="filePickerBtn"></div>
                            <div class="uploadBtn"><var id="lang_start_upload"></var></div>
                        </div>
                    </div>
                    <div id="dndArea" class="placeholder">
                        <div class="filePickerContainer">
                            <div id="filePickerReady"></div>
                        </div>
                    </div>
                    <ul class="filelist element-invisible">
                        <li id="filePickerBlock" class="filePickerBlock"></li>
                    </ul>
                </div>
            </div>

        </div>
    </div>
    <script type="text/javascript" src="script/userPhoto.js"></script>
</body>
</html>