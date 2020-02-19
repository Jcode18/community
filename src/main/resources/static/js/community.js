function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(questionId, 1, content);
}

function comment2target(targetId, type, content) {
    if (!content) {
        alert("不能回复空内容");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (reponse) {
            if (reponse.code == 200) {
                window.location.reload();
            } else {
                if (reponse.code == 2003) {
                    var isAccepted = confirm(reponse.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=bd401d2e275a2503215b&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", true);
                    }

                } else {
                    alert(reponse.message);
                }
            }
        },
        dataType: "json"
    });
}

function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    comment2target(commentId, 2, content);

}

function collapseComment(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);

    var status = e.getAttribute("status");
    if (status) {
        comments.removeClass("in");
        e.removeAttribute("status");
        e.classList.remove("active");
    } else {
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {
            comments.addClass("in");
            e.setAttribute("status", "in");
            e.classList.add("active");
        } else {
            $.getJSON("/comment/" + id, function (data) {

                $.each(data.data.reverse(), function (index, comment) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<div/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarurl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtCreate).format('YYYY.MM.DD')

                    })));

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement)
                        .append(mediaBodyElement);
                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);
                    subCommentContainer.prepend(commentElement);
                });
                comments.addClass("in");
                e.setAttribute("status", "in");
                e.classList.add("active");
            });
        }
    }

}

function selectTag(e) {
    var value=e.getAttribute("data-tag");
    var previous=$("#tag").val();
    if(previous.indexOf(value)==-1){
        if(previous){
            $("#tag").val(previous+','+value);
        }else {
            $("#tag").val(value);
        }
    }
}

function showTag() {
    $("#select-tag").show();
}