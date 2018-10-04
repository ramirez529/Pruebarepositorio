var customerDetails = [];

function setOnLoad() {
    populateCustomerDetails();
}

function populateCustomerDetails() {
    var currUrl = location.href;
    currUrl = currUrl.replace("CjDemo.html", "cjDemo");
    $('#customer').empty();

    $.ajax({
        url: currUrl,
        type: "post",
        dataType: "json",
        data: { "command": "init" },
        success: function (responseData) {
            customerDetails = responseData.customers;

            customerDetails.sort(function (a, b) {
                return (a.accountnum > b.accountnum) - (a.accountnum < b.accountnum);
            });

            var custTable = document.getElementById("customer");
            for (var i = 0; i < customerDetails.length; i++) {
                var option = document.createElement('option');
                var value = customerDetails[i].accountnum + " | " + customerDetails[i].firstname + " " + customerDetails[i].lastname;
                option.label = value;
                option.textContent = value;
                option.setAttribute('value', value);
                option.style.fontFamily = "Lucida Console";
                option.style.fontSize = "13px";
                custTable.appendChild(option);
            }
        },
        error: function (data) {
            alert("Initialization error!");
        },
        async: false
    });
}


function customerSelectionChange(custAccountNum) {
    if (custAccountNum == undefined) {
        var custTable = document.getElementById("customer");
        var selectedCustomer = custTable.options[custTable.selectedIndex].value;
        custAccountNum = selectedCustomer.substring(0, 4);
    }

    //Clear Transaction table
    $('#transaction').empty();

    //Re-populate
    var currUrl = location.href;
    currUrl = currUrl.replace("CjDemo.html", "cjDemo");
   
    $.ajax({
        url: currUrl,
        type: "post",
        dataType: "json",
        data: { "command": "getTransactions", "accountnum": custAccountNum },
        success: function (responseData) {
            var transactions = responseData.transactions;

            transactions.sort(function (a, b) {
                return (a.transdate > b.transdate) - (a.transdate < b.transdate);
            });
            
            var transTable = document.getElementById("transaction");
            for (var i = 0; i < transactions.length; i++) {
                var option = document.createElement('option');
                var padding1 = "";
                for (var j = 40 - transactions[i].merchantname.length; j != 0; j--)
                    padding1 = padding1 + ".";
                var padding2 = "";
                for (j = 10 - transactions[i].amount.length; j > 1; j--)
                    padding2 = padding2 + ".";
                var value = transactions[i].transdate + " | " + transactions[i].transid + " | " + transactions[i].merchantname + padding1 + padding2 + transactions[i].amount;
                option.label = value;
                option.textContent = value;
                option.setAttribute('value', value);
                option.style.fontFamily = "Lucida Console";
                option.style.fontSize = "13px";
                transTable.appendChild(option);
            }
            //$("tr:odd").css("background-color", "yellow");
            transTable.css("background-color", "yellow");
        },
        error: function (data) {
            alert("Unable to get transactions for the selected customer!");
        },
        async: false
    });
}

function transactionSelectionChange() {
    var transTable = document.getElementById("transaction");
    var selectedTransaction = transTable.options[transTable.selectedIndex].value;
}

function handleInsertCustomer() {
    //Throw customer dialog
    document.getElementById("dlgInsertCustomer").disabled = false;
    document.getElementById("dlgUpdateCustomer").disabled = true;
    document.getElementById("firstname").value = "";
    document.getElementById("lastname").value = "";
    document.getElementById("prefemail").checked = false;
    document.getElementById("prefsms").checked = true;
    document.getElementById("prefboth").checked = false;

    $("#customerFormId").dialog("open");
    $("#dlgInsertCustomer").off("click").on("click", function () {
        //Get next customer accountnum
        var lstIds = [];
        for (var i = 0; i < customerDetails.length; i++) {
            lstIds[i] = customerDetails[i].accountnum;
        }
        lstIds.sort();
        var nextAccountNum = (parseInt(lstIds[lstIds.length - 1]) + 1).toString();

        //Get other values from dialog
        var firstname = document.getElementById("firstname").value;
        var lastname = document.getElementById("lastname").value;
        var email = document.getElementById("email").value;
        var phone = document.getElementById("phone").value;
        var preference;
        if (document.getElementById("prefemail").checked)
            preference = document.getElementById("prefemail").value;
        else if (document.getElementById("prefsms").checked)
            preference = document.getElementById("prefsms").value;
        else if (document.getElementById("prefboth").checked)
            preference = document.getElementById("prefboth").value;

        if (firstname == "" || lastname == "" || email == "" || phone == "") {
            alert("Please supply all fields!");
            return;
        }
        var currUrl = location.href;
        currUrl = currUrl.replace("CjDemo.html", "cjDemo");

        $.ajax({
            url: currUrl,
            type: "post",
            dataType: "json",
            data: { "command": "insertCustomer", "accountnum": nextAccountNum, "firstname": firstname, "lastname": lastname, "email": email, "phone": phone, "preference": preference },
            success: function (responseData) {
                var insertResult = responseData.result;

                if (insertResult <= 0) {
                    alert("Unable to insert customer record!");
                }
                else {
                    populateCustomerDetails();
                    $("#customerFormId").dialog("close");
                }
            },
            error: function (data) {
                alert("Error while inserting customer record!");
            },
            async: false
        });
    });
    $("#dlgCancelCustomer").on("click", function () {
        $("#customerFormId").dialog("close");
    });
}

function handleUpdateCustomer() {
    //Get Selected customer rec
    var custTable = document.getElementById("customer");
    if (custTable.selectedIndex < 0) {
        alert("Please select a customer to update!");
        return;
    }
    var selectedCustomer = custTable.options[custTable.selectedIndex].value;
    var selCustAccountNum = selectedCustomer.substring(0, 4);

    for (var i = 0; i < customerDetails.length; i++) {
        if (customerDetails[i].accountnum == selCustAccountNum) {
            document.getElementById("firstname").value = customerDetails[i].firstname;
            document.getElementById("lastname").value = customerDetails[i].lastname;
            document.getElementById("email").value = customerDetails[i].email;
            document.getElementById("phone").value = customerDetails[i].phone;
            document.getElementById("prefemail").checked = document.getElementById("prefsms").checked = document.getElementById("prefboth").checked = false;
            if (customerDetails[i].preference == "email")
                document.getElementById("prefemail").checked = true;
            else if (customerDetails[i].preference == "sms")
                document.getElementById("prefsms").checked = true;
            else if (customerDetails[i].preference == "both")
                document.getElementById("prefboth").checked = true;

            break;
        }
    }
    //Throw customer dialog
    document.getElementById("dlgInsertCustomer").disabled = true;
    document.getElementById("dlgUpdateCustomer").disabled = false;
    $("#customerFormId").dialog("open");

    $("#dlgUpdateCustomer").off("click").on("click", function () {
        //Get selected customer accountnum
        var custTable = document.getElementById("customer");
        var selectedCustomer = custTable.options[custTable.selectedIndex].value;
        var selCustAccountNum = selectedCustomer.substring(0, 4);

        //Get other values from dialog
        var firstname = document.getElementById("firstname").value;
        var lastname = document.getElementById("lastname").value;
        var email = document.getElementById("email").value;
        var phone = document.getElementById("phone").value;
        var preference;
        if (document.getElementById("prefemail").checked)
            preference = document.getElementById("prefemail").value;
        else if (document.getElementById("prefsms").checked)
            preference = document.getElementById("prefsms").value;
        else if (document.getElementById("prefboth").checked)
            preference = document.getElementById("prefboth").value;

        if (firstname == "" || lastname == "" || email == "" || phone == "") {
            alert("Please supply all fields!");
            return;
        }

        var currUrl = location.href;
        currUrl = currUrl.replace("CjDemo.html", "cjDemo");

        $.ajax({
            url: currUrl,
            type: "post",
            dataType: "json",
            data: { "command": "updateCustomer", "accountnum": selCustAccountNum, "firstname": firstname, "lastname": lastname, "email": email, "phone": phone, "preference": preference },
            success: function (responseData) {
                var insertResult = responseData.result;

                if (insertResult <= 0) {
                    alert("Unable to update customer record!");
                }
                else {
                    populateCustomerDetails();
                    $("#customerFormId").dialog("close");
                }
            },
            error: function (data) {
                alert("Error while updating customer record!");
            },
            async: false
        });
    });
    $("#dlgCancelCustomer").on("click", function () {
        $("#customerFormId").dialog("close");
    });
}

function handleDeleteCustomer() {
    //Get selected customer accountnum
    var custTable = document.getElementById("customer");
    if (custTable.selectedIndex < 0) {
        alert("Please select a customer to delete!");
        return;
    }
    var selectedCustomer = custTable.options[custTable.selectedIndex].value;
    var selCustAccountNum = selectedCustomer.substring(0, 4);

    var currUrl = location.href;
    currUrl = currUrl.replace("CjDemo.html", "cjDemo");

    $.ajax({
        url: currUrl,
        type: "post",
        dataType: "json",
        data: { "command": "deleteCustomer", "accountnum": selCustAccountNum },
        success: function (responseData) {
            var insertResult = responseData.result;

            if (insertResult <= 0) {
                alert("Unable to delete customer record!");
            }
            else {
                populateCustomerDetails();
                $("#customerFormId").dialog("close");
            }
        },
        error: function (data) {
            alert("Error while deleting customer record!");
        },
        async: false
    });
}

function handleInsertTransaction() {
    var custTable = document.getElementById("customer");
    if (custTable.selectedIndex < 0) {
        alert("Please select a customer to insert a transaction for!");
        return;
    }
    //Throw transaction dialog
    document.getElementById("dlgInsertTransaction").disabled = false;
    document.getElementById("dlgUpdateTransaction").disabled = true;
    document.getElementById("transdate").value = "";
    document.getElementById("merchantname").value = "";
    document.getElementById("amount").value = "";

    $("#transactionFormId").dialog("open");
    $("#dlgInsertTransaction").off("click").on("click", function () {
        //Get selected customer accountnum
        var custTable = document.getElementById("customer");
        var selectedCustomer = custTable.options[custTable.selectedIndex].value;
        var selCustAccountNum = selectedCustomer.substring(0, 4);

        var currUrl = location.href;
        currUrl = currUrl.replace("CjDemo.html", "cjDemo");

        //Get next Transaction id 
        var nextTransId = 0;
        $.ajax({
            url: currUrl,
            type: "post",
            dataType: "json",
            data: { "command": "getTransactions", "accountnum": "0" },
            success: function (responseData) {
                var transactions = responseData.transactions;

                var lstIds = [];
                for (var i = 0; i < transactions.length; i++) {
                    lstIds[i] = transactions[i].transid;
                }
                lstIds.sort();
                nextTransId = (parseInt(lstIds[lstIds.length - 1]) + 1).toString();
            },
            error: function (data) {
                alert("Unable to generate next transaction id for inserting new transaction!");
            },
            async: false
        });
        
        if (nextTransId == 0) {
            alert("Unable to get next transaction id for inserting new transaction!");
        }

        //Get other values from dialog
        var transdate = document.getElementById("transdate").value;
        var merchantname = document.getElementById("merchantname").value;
        var amount = document.getElementById("amount").value;

        if (transdate == "" || merchantname == "" || amount == "") {
            alert("Please supply all fields!");
            return;
        }
        
        var currUrl = location.href;
        currUrl = currUrl.replace("CjDemo.html", "cjDemo");

        $.ajax({
            url: currUrl,
            type: "post",
            dataType: "json",
            data: { "command": "insertTransaction", "transid": nextTransId, "accountnum": selCustAccountNum, "transdate": transdate, "merchantname": merchantname, "amount": amount },
            success: function (responseData) {
                var insertResult = responseData.result;

                if (insertResult <= 0) {
                    alert("Unable to insert transaction record!");
                }
                else {
                    $("#transactionFormId").dialog("close");
                    customerSelectionChange(selCustAccountNum);
                }
            },
            error: function (data) {
                alert("Error while inserting transaction record!");
            },
            async: false
        });
    });
    $("#dlgCancelTransaction").on("click", function () {
        $("#transactionFormId").dialog("close");
    });
}

function handleUpdateTransaction() {
    //Get selected transaction rec
    var transTable = document.getElementById("transaction");
    if (transTable.selectedIndex < 0) {
        alert("Please select a transaction to update!");
        return;
    }
    var selectedTrans = transTable.options[transTable.selectedIndex].value;
    var selTransId = selectedTrans.substring(13, 17);

    //Get transaction record
    var currUrl = location.href;
    currUrl = currUrl.replace("CjDemo.html", "cjDemo");

    $.ajax({
        url: currUrl,
        type: "post",
        dataType: "json",
        data: { "command": "getTransaction", "transid": selTransId },
        success: function (responseData) {
            var selTransaction = responseData.transaction;

            document.getElementById("transdate").value = selTransaction.transdate;
            document.getElementById("merchantname").value = selTransaction.merchantname;
            document.getElementById("amount").value = selTransaction.amount;
        },
        error: function (data) {
            alert("Unable to get the selected transaction to update step 1!");
        },
        async: false
    });

    //Throw transaction dialog
    document.getElementById("dlgInsertTransaction").disabled = true;
    document.getElementById("dlgUpdateTransaction").disabled = false;
    $("#transactionFormId").dialog("open");

    $("#dlgUpdateTransaction").off("click").on("click", function () {
        //Get selected transid & accountnum
        var transTable = document.getElementById("transaction");
        var selectedTransaction = transTable.options[transTable.selectedIndex].value;
        var selTransId = selectedTransaction.substring(13, 17);
        var selAccountNum;

        //Get transaction record
        var currUrl = location.href;
        currUrl = currUrl.replace("CjDemo.html", "cjDemo");
        var selAccountNum;

        $.ajax({
            url: currUrl,
            type: "post",
            dataType: "json",
            data: { "command": "getTransaction", "transid": selTransId },
            success: function (responseData) {
                var selTransaction = responseData.transaction;
                selAccountNum = selTransaction.accountnum;
            },
            error: function (data) {
                alert("Unable to get the selected transaction to update step 2!");
            },
            async: false
        });

        //Get other values from dialog
        var transdate = document.getElementById("transdate").value;
        var merchantname = document.getElementById("merchantname").value;
        var amount = document.getElementById("amount").value;
        
        if (transdate == "" || merchantname == "" || amount == "") {
            alert("Please supply all fields!");
            return;
        }

        var currUrl = location.href;
        currUrl = currUrl.replace("CjDemo.html", "cjDemo");

        $.ajax({
            url: currUrl,
            type: "post",
            dataType: "json",
            data: { "command": "updateTransaction", "transid": selTransId, "accountnum": selAccountNum, "transdate": transdate, "merchantname": merchantname, "amount": email, "amount": amount },
            success: function (responseData) {
                var insertResult = responseData.result;

                if (insertResult <= 0) {
                    alert("Unable to update transaction record!");
                }
                else {
                    $("#transactionFormId").dialog("close");
                    customerSelectionChange(selAccountNum);
                }
            },
            error: function (data) {
                alert("Error while updating transaction record!");
            },
            async: false
        });
    });
    $("#dlgCancelTransaction").on("click", function () {
        $("#transactionFormId").dialog("close");
    });
}

function handleDeleteTransaction() {
    //Get selected transid & accountnum
    var transTable = document.getElementById("transaction");
    if (transTable.selectedIndex < 0) {
        alert("Please select a transaction to delete!");
        return;
    }
    var selectedTransaction = transTable.options[transTable.selectedIndex].value;
    var selTransId = selectedTransaction.substring(13, 17);
    var selAccountNum = 0;

    var currUrl = location.href;
    currUrl = currUrl.replace("CjDemo.html", "cjDemo");

    $.ajax({
        url: currUrl,
        type: "post",
        dataType: "json",
        data: { "command": "getTransactions", "accountnum": "0" },
        success: function (responseData) {
            var transactions = responseData.transactions;

            for (var i = 0; i < transactions.length; i++) {
                if (transactions[i].transid == selTransId) {
                    selAccountNum = transactions[i].accountnum;
                    break;
                }
            }
        },
        error: function (data) {
            alert("Unable to get transaction's customer account number!");
        },
        async: false
    });
    
    var currUrl = location.href;
    currUrl = currUrl.replace("CjDemo.html", "cjDemo");

    $.ajax({
        url: currUrl,
        type: "post",
        dataType: "json",
        data: { "command": "deleteTransaction", "transid": selTransId },
        success: function (responseData) {
            var insertResult = responseData.result;

            if (insertResult <= 0) {
                alert("Unable to delete transaction record!");
            }
            else {
                if (selAccountNum != 0)
                    customerSelectionChange(selAccountNum);
                $("#customerFormId").dialog("close");
            }
        },
        error: function (data) {
            alert("Error while deleting transaction record!");
        },
        async: false
    });
}

function handleVerifyTransaction() {
    //Get selected transid & accountnum
    var transTable = document.getElementById("transaction");
    if (transTable.selectedIndex < 0) {
        alert("Please select a transaction to verify!");
        return;
    }
    var selectedTransaction = transTable.options[transTable.selectedIndex].value;
    var selTransId = selectedTransaction.substring(13, 17);

    var currUrl = location.href;
    currUrl = currUrl.replace("CjDemo.html", "cjDemo");

    $.ajax({
        url: currUrl,
        type: "post",
        dataType: "json",
        data: { "command": "verifyTransaction", "transid": selTransId },
        success: function (responseData) {
            alert(responseData.responseText);
        },
        error: function (data) {
            alert(data.responseText);
        },
        async: false
    });
}