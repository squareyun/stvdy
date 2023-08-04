<script setup>
import { Configuration, OpenAIApi } from "openai";
import { Form, Field } from "vee-validate";

const configuration = new Configuration({
  // 아래의 organization과 key는 절대 git에 commit 금지!!
  organization: "org-P6R01iSM0pteoADPI2D2m0lq",
  apiKey: "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",
  // 위의 organization과 key는 절대 git에 commit 금지!!
  // commit 전 공란으로 두기 - 차후 별도의 파일로 관리 예정
});

delete configuration.baseOptions.headers['User-Agent'];

const openai = new OpenAIApi(configuration);

async function onSubmit(values) {
  const sampleQuestion = await openai.createChatCompletion({
    model: "gpt-3.5-turbo",
    messages: [
      { "role": "system", "content": values.type },
      { "role": "user", "content": values.question }
    ]
  })
  const answerField = document.getElementById("answerField");
  answerField.innerText = sampleQuestion.data.choices[0].message.content

}




// const sampleQuestion = await openai.createChatCompletion({
//   model: "gpt-3.5-turbo",
//   messages: [
//     { "role": "system", "content": "You are a helpful assistant." },
//     { "role": "user", "content": "Who wrote Hamlet and Macbeth?" }
//   ]
// }
// )

// console.log(sampleQuestion.data.choices[0].message.content)

</script>

<template>
  <div style="color:white">
    Test function - AI Assist
    <Form @submit="onSubmit">
      <Field name="type" type="text" placeholder="답변 스타일" />
      <Field name="question" type="text" placeholder="질문" />
      <button type="submit">물어보기</button>
    </Form>
    <span id="answerField"></span>

  </div>
</template>

<style></style>
