$(document).ready(function(){
    $('.qna-comment-slipp-articles').on("submit",".answer-write", addAnswer);
    $('.qna-comment-slipp-articles').on("submit",".form-delete", deleteAnswer);
    function addAnswer() {
        var form = $(this);
        var url = form.attr('action');
        var queryString = form.serialize();

        $.ajax({
            type : 'post',
            url : url,
            data : queryString,
            dataType : 'json',
            error: onError,
            success : onAddAnswerSuccess
        });

        return false;
    }

    function deleteAnswer(){
        var $form = $(this);
        var url = $form.attr('action');
        $.ajax({
            type : 'DELETE',
            url : url,
            error: onError,
            success : function(message){
                alert(message);
                $form.closest('.article').remove();
            }
        });

        return false;
    }

    function onAddAnswerSuccess(data){
        var answerTemplate = $("#answerTemplate").html();
        var template = answerTemplate.format(data.writer.userId, data.formattedCreateDate, data.contents, data.question.id,
            data.id);
        $(".qna-comment-slipp-articles").prepend(template);
        $("textarea[name=contents]").val("");
    }

    function onError(s){
        var status = s.status;
        if(status === 401){
            alert('로그인 해주세요!')
            location.href='/users/login'
        }else{
            console.log('서버 에러 : ' + status);
        }
    }
});


String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};
