<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.OrderInfo" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ page import="com.chengxusheji.domain.Doctor" %>
<%@ page import="com.chengxusheji.domain.TimeSlot" %>
<%@ page import="com.chengxusheji.domain.VisitState" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的UserInfo信息
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    //获取所有的Doctor信息
    List<Doctor> doctorList = (List<Doctor>)request.getAttribute("doctorList");
    //获取所有的TimeSlot信息
    List<TimeSlot> timeSlotList = (List<TimeSlot>)request.getAttribute("timeSlotList");
    //获取所有的VisitState信息
    List<VisitState> visitStateList = (List<VisitState>)request.getAttribute("visitStateList");
    OrderInfo orderInfo = (OrderInfo)request.getAttribute("orderInfo");

%>
<HTML><HEAD><TITLE>查看预约</TITLE>
<STYLE type=text/css>
body{margin:0px; font-size:12px; background-image:url(<%=basePath%>images/bg.jpg); background-position:bottom; background-repeat:repeat-x; background-color:#A2D5F0;}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
</HEAD>
<BODY><br/><br/>
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3'  class="tablewidth">
  <tr>
    <td width=30%>预约id:</td>
    <td width=70%><%=orderInfo.getOrderId() %></td>
  </tr>

  <tr>
    <td width=30%>预约用户:</td>
    <td width=70%>
      <select name="orderInfo.uesrObj.user_name" disabled>
      <%
        for(UserInfo userInfo:userInfoList) {
          String selected = "";
          if(userInfo.getUser_name().equals(orderInfo.getUesrObj().getUser_name()))
            selected = "selected";
      %>
          <option value='<%=userInfo.getUser_name() %>' <%=selected %>><%=userInfo.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>预约医生:</td>
    <td width=70%>
      <select name="orderInfo.doctor.doctorNo" disabled>
      <%
        for(Doctor doctor:doctorList) {
          String selected = "";
          if(doctor.getDoctorNo().equals(orderInfo.getDoctor().getDoctorNo()))
            selected = "selected";
      %>
          <option value='<%=doctor.getDoctorNo() %>' <%=selected %>><%=doctor.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>预约日期:</td>
        <% java.text.DateFormat orderDateSDF = new java.text.SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><%=orderDateSDF.format(orderInfo.getOrderDate()) %></td>
  </tr>

  <tr>
    <td width=30%>预约时间段:</td>
    <td width=70%>
      <select name="orderInfo.timeSlotObj.timeSlotId" disabled>
      <%
        for(TimeSlot timeSlot:timeSlotList) {
          String selected = "";
          if(timeSlot.getTimeSlotId() == orderInfo.getTimeSlotObj().getTimeSlotId())
            selected = "selected";
      %>
          <option value='<%=timeSlot.getTimeSlotId() %>' <%=selected %>><%=timeSlot.getTimeSlotName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>出诊状态:</td>
    <td width=70%>
      <select name="orderInfo.visiteStateObj.visitStateId" disabled>
      <%
        for(VisitState visitState:visitStateList) {
          String selected = "";
          if(visitState.getVisitStateId() == orderInfo.getVisiteStateObj().getVisitStateId())
            selected = "selected";
      %>
          <option value='<%=visitState.getVisitStateId() %>' <%=selected %>><%=visitState.getVisitStateName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>医生说明:</td>
    <td width=70%><%=orderInfo.getIntroduce() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="返回" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
