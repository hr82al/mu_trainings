$(function () {
  // $(".date").click(function () {
  //   getUserIDTrainingId(this);
  //   tmp = this;
  // });
  $(".date").datepicker({
    format: "yyyy-mm-dd",
    language: "ru",
    autoclose: true,
  });

  $(".date").on("changeDate", function (e) {
    date = e.date.toJSON().substr(0, 10);
    const IDs = getUserIDTrainingId(this);
    console.log(
      `insert into last dates user id : ${IDs.userId}, training id: ${IDs.trainingID}, date: ${date}`
    );
  });
});

var tmp = "";
function getUserIDTrainingId(p) {
  const x = parseInt($(p).index());
  const y = parseInt($(p).parent().index()) + 1;
  const trainingID = $(`#trainings tr:eq(0) th:eq(${x})`).attr("id").substr(3);
  const userID = $(`#trainings tr:eq(${y})`).attr("id").substr(3);
  console.log(`${x} : ${y} - ${trainingID} , ${userID}`);
  return {
    trainingID: trainingID,
    userId: userID,
  };
}
