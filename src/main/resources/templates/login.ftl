<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>

<body>
    <div>
        <h2>ROADTECHsss</h2>
        <!-- <form id="form-login" method="post" onsubmit="return false;"> -->
        <form id="__cont_lgn_form" action="/login-proc" method="POST">
            <input type="text" name="username" placeholder="username" /><br>
            <input type="password" name="password" placeholder="password" /><br>
            <input type="submit" value="SignIn" />
        </form>
        <!-- test -->
         <button id="fetch_id">fetch</button>
    </div>
</body>
<script>
    document.getElementById("fetch_id").addEventListener("click", function (params) {
        fetch("/login-proc", {
            method: "POST",
            body: JSON.stringify({
                username: "test",
                password: "123qwe",
            }),
            })
            .then((response) => response.json())
            .then((result) => console.log(result));
    })

</script>
</html>