let host = "";
let varification_code = "3b4gBY4C75sx8DD9lvZL0mM1wLGnysYHFERx9136YnURDBbXwb";

// set store
function setStore(data) {
    var count = 1;
    $("#itemAccountId").text(data["username"]);
    data["singleItemOffers"].forEach((offerItem) => {
        $("#itemName" + count).text(offerItem["displayName"]);
        $("#itemCost" + count).text(offerItem["cost"]);
        $("#itemImage" + count).attr("src", offerItem["displayIcon"]);
        count += 1;
    });
    $("#itemCards").removeClass("invisible");
    $("#itemCards").addClass("visible");
    setVisible("home");
}
// login
function login() {
    if ($("#loginUsername").val().length < 1 || $("#loginPassword").val().length < 1) {
        return;
    }
    $("#directLogin-statusMessage").text("ログイン中...")
    $.ajax({
        url:`${host}/login`,
        type:"POST",
        contentType: "application/json",
        dataType: "json",
        crossDomain: true,
        data:JSON.stringify({
            "username":$("#loginUsername").val(),
            "password":$("#loginPassword").val(),
            "remember":$("#loginRemember").prop("checked")
        })
    }).done((data) => {
        setStore(data);
        $("#directLogin-statusMessage").text("ログイン成功");
        //$("#loginUsername").val("");
        //$("#loginPassword").val("");
    }).fail(() => {
        $("#directLogin-statusMessage").text("ログイン失敗");
        //$("#loginPassword").val("");
    })
}

// container
var currentContainer = "home";
var containers = [ "directLogin", "home", "accounts", "webhook" ];
// accounts
var savedAccount = null;
var txtAccount = null;

// setvisible container
function setVisible(targetContainer) {
    if (!containers.includes(targetContainer)) {
        return
    }
    containers.forEach((container) => {
        if (container == targetContainer) {
            $("#" + container + "Button").addClass("selected");
            $("#" + container).addClass("visible");
            $("#" + container).removeClass("invisible");
            return;
        }
        $("#" + container).addClass("invisible");
        $("#" + container).removeClass("visible");
        $("#" + container + "Button").removeClass("selected");
    });
    currentContainer = targetContainer;
}

// register containers
containers.forEach((container) => {
    $("#" + container + "Button").on("click", function() {
        setVisible(container);
    });
});

// set message to current container
function setStatusMessage(message) {
    $("#" + currentContainer + "-statusMessage").text(message);
}

// load accounts
function loadAccounts() {
    setStatusMessage("ロード中...");
    $.ajax({
        url:`${host}/accounts`,
        type:"GET",
        crossDomain: true
    }).done((data) => {
        var saved = data["saved"];
        var txt = data["txt"];
        $("#savedAccounts-Dropdown").empty();
        $("#savedAccounts-Text").text(`saved accounts: ${saved.length}`)
        saved.forEach((user) => {
            $("#savedAccounts-Dropdown").append(`<li><a class="dropdown-item" href="#" id="savedAccount-${user}">${user}</a></li>`);
            $(`#savedAccount-${user}`).on("click", () => {
                setSavedAccount(user);
            });
        });
        if (saved.length > 0) {
            setSavedAccount(saved[0]);
        }
        $("#txtAccounts-Dropdown").empty();
        $("#txtAccounts-Text").text(`txt accounts: ${txt.length}`)
        txt.forEach((user) => {
            $("#txtAccounts-Dropdown").append(`<li><a class="dropdown-item" href="#" id="txtAccount-${user}">${user}</a></li>`)
            $(`#txtAccount-${user}`).on("click", () => {
                setTxtAccount(user);
            });
        });
        if (txt.length > 0) {
            setTxtAccount(txt[0]);
        }
        setStatusMessage("ロード完了");
    })
    .fail(() => {
       setStatusMessage("ロード失敗");
    });
}

// set saved account
function setSavedAccount(user) {
    $("#savedAccounts-Button").text(user);
    savedAccount = user;
    console.log(`set saved account: \"${user}\"`);
}

// set txt account
function setTxtAccount(user) {
    $("#txtAccounts-Button").text(user);
    txtAccount = user;
    console.log(`set txt account: \"${user}\"`);
}

// login saved account
function savedLogin() {
    if (savedAccount != null) {
        setStatusMessage("ログイン中");
        $.ajax({
            url:`${host}/saved/login`,
            type:"POST",
            contentType: "application/json",
            dataType: "json",
            crossDomain: true,
            data:JSON.stringify({
                "username":savedAccount
            })
        }).done((data) => {
            setStore(data);
            setStatusMessage("ログイン成功");
        }).fail(() => {
            setStatusMessage("ログイン失敗");
        })
    }
}

// login txt account
function txtLogin() {
    if (txtAccount != null) {
        setStatusMessage("ログイン中");
        $.ajax({
            url:`${host}/txt/login`,
            type:"POST",
            contentType: "application/json",
            dataType: "json",
            crossDomain: true,
            data:JSON.stringify({
                "username":txtAccount
            })
        }).done((data) => {
            setStore(data);
            setStatusMessage("ログイン成功");
        }).fail(() => {
            setStatusMessage("ログイン失敗");
        })
    }
}

// send webhook
function executeWebhook() {
    setStatusMessage("送信中...");
    $.ajax({
        url:`${host}/execute`,
        type:"POST",
        headers:{
            "varification_code":varification_code
        },
        crossDomain:true
    }).done((data)=>{
        setStatusMessage("送信完了");
    }).fail(() => {
        setStatusMessage("送信失敗");
    })
}

// set button events
function setEvents() {
    $("#submitLogin").on("click", login);
    $("#executeWebhook-Button").on("click", executeWebhook);
    $("#reloadAccounts-Button").on("click", loadAccounts);
    $("#loginTxtAccountButton").on("click", txtLogin);
    $("#loginSavedAccountButton").on("click", savedLogin);
}

setEvents();
loadAccounts();