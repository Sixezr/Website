<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title}</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/logo.png" type="image/x-icon">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

    <style>
        @media (max-width: 520px) {
            * {
                font-size: 0.8rem;
            }
        }

        form {
            width: ${form_width}%;
            margin-left: auto;
            margin-right: auto;
        }
    </style>
</head>
<body>
<%@include file="/WEB-INF/views/components/_nav.jsp"%>
