<%@ page import="java.sql.*" language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!--[if lt IE 9]>
<script src='<%=basePath%>/js/html5shiv.js' type='text/javascript'></script>
<![endif]-->
<link href='<%=basePath%>/css/bootstrap/bootstrap.css' media='all' rel='stylesheet' type='text/css' />
<link href='<%=basePath%>/css/bootstrap/bootstrap-responsive.css' media='all' rel='stylesheet' type='text/css' />
<!-- / jquery ui -->
<link href='<%=basePath%>/css/jquery_ui/jquery-ui-1.10.0.custom.css' media='all' rel='stylesheet' type='text/css' />
<link href='<%=basePath%>/css/jquery_ui/jquery.ui.1.10.0.ie.css' media='all' rel='stylesheet' type='text/css' />
<!-- / datatables -->
<link href='<%=basePath%>/css/plugins/datatables/bootstrap-datatable.css' media='all' rel='stylesheet' type='text/css' />
<!-- / flags (country flags) -->
<link href='<%=basePath%>/css/plugins/flags/flags.css' media='all' rel='stylesheet' type='text/css' />
<!-- / slider nav (address book) -->
<link href='<%=basePath%>/css/plugins/slider_nav/slidernav.css' media='all' rel='stylesheet' type='text/css' />
<!-- / fuelux (wizard) -->
<link href='<%=basePath%>/css/plugins/fuelux/wizard.css' media='all' rel='stylesheet' type='text/css' />
<!-- / flatty theme -->
<link href='<%=basePath%>/css/light-theme.css' id='color-settings-body-color' media='all' rel='stylesheet' type='text/css' />
<!-- / self -->
<link href='<%=basePath%>/css/custom.css' media='all' rel='stylesheet' type='text/css' />
<link href="<%=basePath%>/css/iconfont/iconfont.css" rel="stylesheet" type="text/css" charset="utf-8"/>

<!-- / jquery -->
<script src='<%=basePath%>/js/jquery/jquery.min.js' type='text/javascript'></script>
<!-- / jquery mobile events (for touch and slide) -->
<script src='<%=basePath%>/js/plugins/mobile_events/jquery.mobile-events.min.js' type='text/javascript'></script>
<!-- / jquery migrate (for compatibility with new jquery) -->
<script src='<%=basePath%>/js/jquery/jquery-migrate.min.js' type='text/javascript'></script>
<!-- / jquery ui -->
<script src='<%=basePath%>/js/jquery_ui/jquery-ui.min.js' type='text/javascript'></script>
<!-- / bootstrap -->
<script src='<%=basePath%>/js/bootstrap/bootstrap.min.js' type='text/javascript'></script>
<script src='<%=basePath%>/js/plugins/flot/excanvas.js' type='text/javascript'></script>
<!-- / datatables -->
<script src='<%=basePath%>/js/plugins/datatables/jquery.dataTables.min.js' type='text/javascript'></script>
<script src='<%=basePath%>/js/plugins/datatables/jquery.dataTables.columnFilter.js' type='text/javascript'></script>
<!-- / wysihtml5 -->
<script src='<%=basePath%>/js/plugins/common/wysihtml5.min.js' type='text/javascript'></script>
<script src='<%=basePath%>/js/plugins/common/bootstrap-wysihtml5.js' type='text/javascript'></script>
<!-- / select2 -->
<script src='<%=basePath%>/js/plugins/select2/select2.js' type='text/javascript'></script>
<!-- / color picker -->
<script src='<%=basePath%>/js/plugins/bootstrap_colorpicker/bootstrap-colorpicker.min.js' type='text/javascript'></script>
<!-- / mention -->
<script src='<%=basePath%>/js/plugins/mention/mention.min.js' type='text/javascript'></script>
<!-- / input mask -->
<script src='<%=basePath%>/js/plugins/input_mask/bootstrap-inputmask.min.js' type='text/javascript'></script>
<!-- / fileinput -->
<script src='<%=basePath%>/js/plugins/fileinput/bootstrap-fileinput.js' type='text/javascript'></script>
<!-- / modernizr -->
<script src='<%=basePath%>/js/plugins/modernizr/modernizr.min.js' type='text/javascript'></script>
<!-- / retina -->
<script src='<%=basePath%>/js/plugins/retina/retina.js' type='text/javascript'></script>
<!-- / timeago -->
<script src='<%=basePath%>/js/plugins/timeago/jquery.timeago.js' type='text/javascript'></script>
<!-- / slimscroll -->
<script src='<%=basePath%>/js/plugins/slimscroll/jquery.slimscroll.min.js' type='text/javascript'></script>
<!-- / autosize (for textareas) -->
<script src='<%=basePath%>/js/plugins/autosize/jquery.autosize-min.js' type='text/javascript'></script>
<!-- / charCount -->
<script src='<%=basePath%>/js/plugins/charCount/charCount.js' type='text/javascript'></script>
<!-- / validate -->
<script src='<%=basePath%>/js/plugins/validate/jquery.validate.min.js' type='text/javascript'></script>
<script src='<%=basePath%>/js/plugins/validate/additional-methods.js' type='text/javascript'></script>
<!-- / naked password -->
<script src='<%=basePath%>/js/plugins/naked_password/naked_password-0.2.4.min.js' type='text/javascript'></script>
<!-- / nestable -->
<script src='<%=basePath%>/js/plugins/nestable/jquery.nestable.js' type='text/javascript'></script>
<!-- / tabdrop -->
<script src='<%=basePath%>/js/plugins/tabdrop/bootstrap-tabdrop.js' type='text/javascript'></script>
<!-- / jgrowl -->
<script src='<%=basePath%>/js/plugins/jgrowl/jquery.jgrowl.min.js' type='text/javascript'></script>
<!-- / bootbox -->
<script src='<%=basePath%>/js/plugins/bootbox/bootbox.min.js' type='text/javascript'></script>
<!-- / inplace editing -->
<script src='<%=basePath%>/js/plugins/xeditable/bootstrap-editable.min.js' type='text/javascript'></script>
<script src='<%=basePath%>/js/plugins/xeditable/wysihtml5.js' type='text/javascript'></script>
<!-- / ckeditor -->
<script src='<%=basePath%>/js/plugins/ckeditor/ckeditor.js' type='text/javascript'></script>
<!-- / filetrees -->
<script src='<%=basePath%>/js/plugins/dynatree/jquery.dynatree.min.js' type='text/javascript'></script>
<!-- / datetime picker -->
<script src='<%=basePath%>/js/plugins/bootstrap_datetimepicker/bootstrap-datetimepicker.js' type='text/javascript'></script>
<!-- / daterange picker -->
<script src='<%=basePath%>/js/plugins/bootstrap_daterangepicker/moment.min.js' type='text/javascript'></script>
<script src='<%=basePath%>/js/plugins/bootstrap_daterangepicker/bootstrap-daterangepicker.js' type='text/javascript'></script>
<!-- / max length -->
<script src='<%=basePath%>/js/plugins/bootstrap_maxlength/bootstrap-maxlength.min.js' type='text/javascript'></script>
<!-- / dropdown hover -->
<script src='<%=basePath%>/js/plugins/bootstrap_hover_dropdown/twitter-bootstrap-hover-dropdown.min.js' type='text/javascript'></script>
<!-- / slider nav (address book) -->
<script src='<%=basePath%>/js/plugins/slider_nav/slidernav-min.js' type='text/javascript'></script>
<!-- / fuelux -->
<script src='<%=basePath%>/js/plugins/fuelux/wizard.js' type='text/javascript'></script>
<!-- / flatty theme -->
<script src='<%=basePath%>/js/nav.js' type='text/javascript'></script>
<script src='<%=basePath%>/js/tables.js' type='text/javascript'></script>
<script src='<%=basePath%>/js/theme.js' type='text/javascript'></script>

