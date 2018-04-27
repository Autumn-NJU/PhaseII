$("#register").on('click', function () {
    if ($("#password").val() != $("#surePassword").val())
        alert("Please enter the same password");
    else {

        if (typeof UserType == "undefined") {
            var UserType = {};
            UserType.Worker = "Worker";
            UserType.Requester = "Requester";
        }

        var user = $('#username').val();
        var password = $('#password').val();
        var roleVal = $('#role').val();
        var role;
        if (roleVal == UserType.Worker)
            role = UserType.Worker;
        else
            role = UserType.Requester;

        $.ajax({
            type: "POST",
            url: "/register",
            dataType: "json",
            data: {
                user: user,
                password: password,
                role: role
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