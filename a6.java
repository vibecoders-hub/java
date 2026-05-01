<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Fibonacci & Prime</title>
</head>
<body>

<h3>First 10 Fibonacci Numbers:</h3>
<%
    int a = 0, b = 1, c;
    out.print(a + " " + b + " ");
    for(int i = 3; i <= 10; i++) {
        c = a + b;
        out.print(c + " ");
        a = b;
        b = c;
    }
%>

<h3>First 10 Prime Numbers:</h3>
<%
    int count = 0, num = 2;

    while(count < 10) {
        boolean isPrime = true;

        for(int i = 2; i <= num/2; i++) {
            if(num % i == 0) {
                isPrime = false;
                break;
            }
        }

        if(isPrime) {
            out.print(num + " ");
            count++;
        }

        num++;
    }
%>

</body>
</html>
