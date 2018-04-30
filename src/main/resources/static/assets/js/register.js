$("#register").on('click', function () {
    if ($("#password").val() != $("#surePassword").val())
        alert("Please enter the same password");
    else {

        var user = $('#username').val();
        var password = $('#password').val();
        var role = $('#role').val();

        $.ajax({
            type: "POST",
            url: "/register",
            dataType: "json",
            data: {
                user: user,
                password: password,
                userType: role
            },

            success: function (data) {
                if (data.success) {
                    window.location.href = "index.html";
                } else
                    alert(data.message);
            },
            error: function (data) {
                alert(data.message);
            }
        });
    }
});