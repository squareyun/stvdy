import{e as f,s as h,o,c as s,a as i,b as t,u,F as w,l as y,m as v,t as l,r as c}from"./index-4903a14b.js";const C={style:{color:"white"}},g={key:0},A=["onClick"],B={__name:"QuestionList",setup(S){const e=f(),{questions:r}=h(e);e.getAll();function d(){e.sortMostRecent()}function p(){e.sortNoAnswer()}function m(){e.sortActivated()}async function _(a){await e.getById(a),e.pickedQtn=a,c.push({name:"qtndetail"})}function k(){e.pickedQtn=null,c.push({name:"createqtn"})}return(a,b)=>(o(),s("div",C,[i(" 질문 게시판.vue "),t("button",{onClick:k},"질문 작성하기"),t("form",{action:""},[t("label",null,[t("input",{type:"radio",name:"listSort",value:"newest",onClick:d}),i("최신순")]),t("label",null,[t("input",{type:"radio",name:"listSort",value:"noAnswer",onClick:p}),i("답변없음")]),t("label",null,[t("input",{type:"radio",name:"listSort",value:"answered",onClick:m}),i("활성됨")])]),u(r).length?(o(),s("div",g,[(o(!0),s(w,null,y(u(r),n=>(o(),s("tr",{key:n.id,onClick:N=>_(n.id)},[t("td",null,l(n.id),1),t("td",null,l(n.title),1),t("td",null,l(n.detail),1),t("td",null,l(n.regist_time),1)],8,A))),128))])):v("",!0)]))}};export{B as default};
