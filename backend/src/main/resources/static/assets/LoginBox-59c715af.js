import{F as n,a as g}from"./vee-validate.esm-b09b8c3f.js";import{f,g as w,o as k,c as v,d as s,a as t,w as l,u as o,r as x,b as e,t as _,n as r}from"./index-f0d74d49.js";import{c as b,a as p}from"./index.esm-e7038182.js";import"./_commonjsHelpers-de833af9.js";const y=s("h2",{class:"ents-title"},'"만나게 되어 반가워요"',-1),L={class:"ents"},V={class:"field-name"},B={class:"error-yup"},H={class:"field-name"},S={class:"error-yup"},C={id:"keep-login"},F=["disabled"],N=s("svg",{width:"64",height:"64",viewBox:"0 0 128 128",xmlns:"http://www.w3.org/2000/svg"},[s("path",{d:"M57.6 28.4444L48.64 38.4L65.28 56.8889H0V71.1111H65.28L48.64 89.6L57.6 99.5556L89.6 64L57.6 28.4444ZM115.2 113.778H64V128H128V0H64V14.2222H115.2V113.778Z"})],-1),M=[N],Z={id:"options"},$=s("br",null,null,-1),T={__name:"LoginBox",setup(q){const c=f(),m=b().shape({email:p().email("*올바른 입력이 필요합니다."),password:p().required("*올바른 입력이 필요합니다.")}),u=async a=>{a.keeplog=a.keeplog==="keeplog",await c.login(a),c.isLogin&&x.push("/")};return(a,z)=>{const d=w("router-link");return k(),v("div",null,[y,s("div",L,[t(o(g),{autocomplete:"off",onSubmit:u,"validation-schema":o(m)},{default:l(({errors:i,isSubmitting:h})=>[s("p",V,[e("   이메일 "),s("span",B,_(i.email),1),e("   ")]),t(o(n),{name:"email",type:"text",class:r(["field",{"is-invalid":i.email}])},null,8,["class"]),s("p",H,[e("   비밀번호 "),s("span",S,_(i.password),1),e("   ")]),t(o(n),{name:"password",type:"password",class:r(["field",{"is-invalid":i.password}])},null,8,["class"]),s("div",C,[e(" 로그인 유지 "),t(o(n),{name:"keeplog",type:"checkbox",value:"keeplog",class:"form-control"}),s("button",{id:"login-button",disabled:h},M,8,F)])]),_:1},8,["validation-schema"])]),s("div",Z,[t(d,{class:"link",to:"regist"},{default:l(()=>[e(" 회원가입 ")]),_:1}),$,t(d,{class:"link",to:"passwordReset"},{default:l(()=>[e(" 비밀번호를 잊으셨나요? ")]),_:1})])])}}};export{T as default};
