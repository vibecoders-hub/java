<style>
    table {
        background-color: aqua;
        width: 200px;
        margin-top: 100px;
        margin-left: auto;
        margin-right: auto;
        border: solid 2px;
    }
    td {
        padding: 5px;
        border-spacing: 5px;
    }
</style>

<form method="post" action="Voting">
    <table>
        <tr>
            <td>Name</td>
            <td><input type="text" name="Uname"></td>
        </tr>
        <tr>
            <td>Age</td>
            <td><input type="text" name="Age"></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" name="Btn" value="Check elegibility">
            </td>
        </tr>
    </table>
</form>


  String name = request.getParameter("Uname");
int age = Integer.parseInt(request.getParameter("Age"));

if (age > 18) {
    out.println("<h3 style=\"color:green\">" + name + " you are eligible to vote</h3>");
} else {
    out.println("<h3 style=\"color:brown\">" + name + " you are not eligible to vote</h3>");
}

out.println("<a href=\"index.html\">Home</a>");
