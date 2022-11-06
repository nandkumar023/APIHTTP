$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("src/main/java/Features/postcall.feature");
formatter.feature({
  "line": 1,
  "name": "post call method for blue yonder",
  "description": "",
  "id": "post-call-method-for-blue-yonder",
  "keyword": "Feature"
});
formatter.before({
  "duration": 45441297,
  "status": "passed"
});
formatter.before({
  "duration": 145125,
  "status": "passed"
});
formatter.before({
  "duration": 1052592,
  "status": "passed"
});
formatter.scenario({
  "line": 3,
  "name": "post call scenerio for blue yonder",
  "description": "",
  "id": "post-call-method-for-blue-yonder;post-call-scenerio-for-blue-yonder",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 5,
  "name": "Hit the post method",
  "keyword": "Given "
});
formatter.match({
  "location": "POST.hit_the_post_method()"
});
formatter.result({
  "duration": 2538737357,
  "error_message": "org.json.JSONException: A JSONObject text must begin with \u0027{\u0027 at 1 [character 2 line 1]\n\tat org.json.JSONTokener.syntaxError(JSONTokener.java:433)\n\tat org.json.JSONObject.\u003cinit\u003e(JSONObject.java:195)\n\tat org.json.JSONObject.\u003cinit\u003e(JSONObject.java:319)\n\tat stepDefinitions.POST.hit_the_post_method(POST.java:44)\n\tat ✽.Given Hit the post method(src/main/java/Features/postcall.feature:5)\n",
  "status": "failed"
});
formatter.after({
  "duration": 180419,
  "status": "passed"
});
});