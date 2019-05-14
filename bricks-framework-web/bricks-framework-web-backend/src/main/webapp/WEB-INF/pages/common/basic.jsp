<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
    <head>
        <title><tiles:getAsString name="title" /></title>
        <!-- Script Header -->
        <tiles:insertAttribute name="script-header" />
    </head>
    <body>
        <!-- Header -->
        <tiles:insertAttribute name="header" />
        <!-- Body -->
        <tiles:insertAttribute name="body" />
        <!-- Footer -->
        <tiles:insertAttribute name="footer" />
    </body>
</html>