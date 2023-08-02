import {
  v as on,
  x as de,
  y as M,
  z as sn,
  A as dn,
  B as ie,
  u as m,
  C as cn,
  D as fn,
  E as Ae,
  G as fe,
  H as Cn,
  i as ae,
  I as Fe,
  J as be,
  K as In,
  L as Pn,
  M as wn,
  N as we,
  O as Bn,
} from "./index-4903a14b.js";
/**
 * vee-validate v4.11.0
 * (c) 2023 Abdelrahman Awad
 * @license MIT
 */ function D(e) {
  return typeof e == "function";
}
function vn(e) {
  return e == null;
}
const ge = (e) =>
  e !== null && !!e && typeof e == "object" && !Array.isArray(e);
function We(e) {
  return Number(e) >= 0;
}
function kn(e) {
  const n = parseFloat(e);
  return isNaN(n) ? e : n;
}
function Tn(e) {
  return typeof e == "object" && e !== null;
}
function Nn(e) {
  return e == null
    ? e === void 0
      ? "[object Undefined]"
      : "[object Null]"
    : Object.prototype.toString.call(e);
}
function Un(e) {
  if (!Tn(e) || Nn(e) !== "[object Object]") return !1;
  if (Object.getPrototypeOf(e) === null) return !0;
  let n = e;
  for (; Object.getPrototypeOf(n) !== null; ) n = Object.getPrototypeOf(n);
  return Object.getPrototypeOf(e) === n;
}
function Me(e, n) {
  return (
    Object.keys(n).forEach((t) => {
      if (Un(n[t])) {
        e[t] || (e[t] = {}), Me(e[t], n[t]);
        return;
      }
      e[t] = n[t];
    }),
    e
  );
}
function _e(e) {
  const n = e.split(".");
  if (!n.length) return "";
  let t = String(n[0]);
  for (let r = 1; r < n.length; r++) {
    if (We(n[r])) {
      t += `[${n[r]}]`;
      continue;
    }
    t += `.${n[r]}`;
  }
  return t;
}
const Rn = {};
function zn(e) {
  return Rn[e];
}
const Ge = Symbol("vee-validate-form"),
  Dn = Symbol("vee-validate-field-instance"),
  Ce = Symbol("Default empty value"),
  Ln = typeof window < "u";
function De(e) {
  return D(e) && !!e.__locatorRef;
}
function ve(e) {
  return !!e && D(e.parse) && e.__type === "VVTypedSchema";
}
function Ie(e) {
  return !!e && D(e.validate);
}
function je(e) {
  return e === "checkbox" || e === "radio";
}
function $n(e) {
  return ge(e) || Array.isArray(e);
}
function Hn(e) {
  return Array.isArray(e)
    ? e.length === 0
    : ge(e) && Object.keys(e).length === 0;
}
function Be(e) {
  return /^\[.+\]$/i.test(e);
}
function Wn(e) {
  return hn(e) && e.multiple;
}
function hn(e) {
  return e.tagName === "SELECT";
}
function Gn(e, n) {
  const t =
    ![!1, null, void 0, 0].includes(n.multiple) && !Number.isNaN(n.multiple);
  return e === "select" && "multiple" in n && t;
}
function Kn(e, n) {
  return !Gn(e, n) && n.type !== "file" && !je(n.type);
}
function mn(e) {
  return Ke(e) && e.target && "submit" in e.target;
}
function Ke(e) {
  return e
    ? !!(
        (typeof Event < "u" && D(Event) && e instanceof Event) ||
        (e && e.srcElement)
      )
    : !1;
}
function Ze(e, n) {
  return n in e && e[n] !== Ce;
}
function W(e, n) {
  if (e === n) return !0;
  if (e && n && typeof e == "object" && typeof n == "object") {
    if (e.constructor !== n.constructor) return !1;
    var t, r, l;
    if (Array.isArray(e)) {
      if (((t = e.length), t != n.length)) return !1;
      for (r = t; r-- !== 0; ) if (!W(e[r], n[r])) return !1;
      return !0;
    }
    if (e instanceof Map && n instanceof Map) {
      if (e.size !== n.size) return !1;
      for (r of e.entries()) if (!n.has(r[0])) return !1;
      for (r of e.entries()) if (!W(r[1], n.get(r[0]))) return !1;
      return !0;
    }
    if (xe(e) && xe(n))
      return !(
        e.size !== n.size ||
        e.name !== n.name ||
        e.lastModified !== n.lastModified ||
        e.type !== n.type
      );
    if (e instanceof Set && n instanceof Set) {
      if (e.size !== n.size) return !1;
      for (r of e.entries()) if (!n.has(r[0])) return !1;
      return !0;
    }
    if (ArrayBuffer.isView(e) && ArrayBuffer.isView(n)) {
      if (((t = e.length), t != n.length)) return !1;
      for (r = t; r-- !== 0; ) if (e[r] !== n[r]) return !1;
      return !0;
    }
    if (e.constructor === RegExp)
      return e.source === n.source && e.flags === n.flags;
    if (e.valueOf !== Object.prototype.valueOf)
      return e.valueOf() === n.valueOf();
    if (e.toString !== Object.prototype.toString)
      return e.toString() === n.toString();
    for (l = Object.keys(e), t = l.length, r = t; r-- !== 0; ) {
      var u = l[r];
      if (!W(e[u], n[u])) return !1;
    }
    return !0;
  }
  return e !== e && n !== n;
}
function xe(e) {
  return Ln ? e instanceof File : !1;
}
function en(e, n, t) {
  typeof t.value == "object" && (t.value = w(t.value)),
    !t.enumerable ||
    t.get ||
    t.set ||
    !t.configurable ||
    !t.writable ||
    n === "__proto__"
      ? Object.defineProperty(e, n, t)
      : (e[n] = t.value);
}
function w(e) {
  if (typeof e != "object") return e;
  var n = 0,
    t,
    r,
    l,
    u = Object.prototype.toString.call(e);
  if (
    (u === "[object Object]"
      ? (l = Object.create(e.__proto__ || null))
      : u === "[object Array]"
      ? (l = Array(e.length))
      : u === "[object Set]"
      ? ((l = new Set()),
        e.forEach(function (s) {
          l.add(w(s));
        }))
      : u === "[object Map]"
      ? ((l = new Map()),
        e.forEach(function (s, h) {
          l.set(w(h), w(s));
        }))
      : u === "[object Date]"
      ? (l = new Date(+e))
      : u === "[object RegExp]"
      ? (l = new RegExp(e.source, e.flags))
      : u === "[object DataView]"
      ? (l = new e.constructor(w(e.buffer)))
      : u === "[object ArrayBuffer]"
      ? (l = e.slice(0))
      : u.slice(-6) === "Array]" && (l = new e.constructor(e)),
    l)
  ) {
    for (r = Object.getOwnPropertySymbols(e); n < r.length; n++)
      en(l, r[n], Object.getOwnPropertyDescriptor(e, r[n]));
    for (n = 0, r = Object.getOwnPropertyNames(e); n < r.length; n++)
      (Object.hasOwnProperty.call(l, (t = r[n])) && l[t] === e[t]) ||
        en(l, t, Object.getOwnPropertyDescriptor(e, t));
  }
  return l || e;
}
function Ye(e) {
  return Be(e) ? e.replace(/\[|\]/gi, "") : e;
}
function Y(e, n, t) {
  return e
    ? Be(n)
      ? e[Ye(n)]
      : (n || "")
          .split(/\.|\[(\d+)\]/)
          .filter(Boolean)
          .reduce((l, u) => ($n(l) && u in l ? l[u] : t), e)
    : t;
}
function ye(e, n, t) {
  if (Be(n)) {
    e[Ye(n)] = t;
    return;
  }
  const r = n.split(/\.|\[(\d+)\]/).filter(Boolean);
  let l = e;
  for (let u = 0; u < r.length; u++) {
    if (u === r.length - 1) {
      l[r[u]] = t;
      return;
    }
    (!(r[u] in l) || vn(l[r[u]])) && (l[r[u]] = We(r[u + 1]) ? [] : {}),
      (l = l[r[u]]);
  }
}
function Re(e, n) {
  if (Array.isArray(e) && We(n)) {
    e.splice(Number(n), 1);
    return;
  }
  ge(e) && delete e[n];
}
function nn(e, n) {
  if (Be(n)) {
    delete e[Ye(n)];
    return;
  }
  const t = n.split(/\.|\[(\d+)\]/).filter(Boolean);
  let r = e;
  for (let u = 0; u < t.length; u++) {
    if (u === t.length - 1) {
      Re(r, t[u]);
      break;
    }
    if (!(t[u] in r) || vn(r[t[u]])) break;
    r = r[t[u]];
  }
  const l = t.map((u, s) => Y(e, t.slice(0, s).join(".")));
  for (let u = l.length - 1; u >= 0; u--)
    if (Hn(l[u])) {
      if (u === 0) {
        Re(e, t[0]);
        continue;
      }
      Re(l[u - 1], t[u - 1]);
    }
}
function le(e) {
  return Object.keys(e);
}
function yn(e, n = void 0) {
  const t = we();
  return (t == null ? void 0 : t.provides[e]) || wn(e, n);
}
function tn(e, n, t) {
  if (Array.isArray(e)) {
    const r = [...e],
      l = r.findIndex((u) => W(u, n));
    return l >= 0 ? r.splice(l, 1) : r.push(n), r;
  }
  return W(e, n) ? t : n;
}
function rn(e, n = 0) {
  let t = null,
    r = [];
  return function (...l) {
    return (
      t && clearTimeout(t),
      (t = setTimeout(() => {
        const u = e(...l);
        r.forEach((s) => s(u)), (r = []);
      }, n)),
      new Promise((u) => r.push(u))
    );
  };
}
function Yn(e, n) {
  return ge(n) && n.number ? kn(e) : e;
}
function Le(e, n) {
  let t;
  return async function (...l) {
    const u = e(...l);
    t = u;
    const s = await u;
    return u !== t || ((t = void 0), n(s, l)), s;
  };
}
function $e(e) {
  return Array.isArray(e) ? e : e ? [e] : [];
}
function pe(e, n) {
  const t = {};
  for (const r in e) n.includes(r) || (t[r] = e[r]);
  return t;
}
function qn(e) {
  let n = null,
    t = [];
  return function (...r) {
    const l = be(() => {
      if (n !== l) return;
      const u = e(...r);
      t.forEach((s) => s(u)), (t = []), (n = null);
    });
    return (n = l), new Promise((u) => t.push(u));
  };
}
const gn = (e, n, t) =>
  n.slots.default
    ? typeof e == "string" || !e
      ? n.slots.default(t())
      : {
          default: () => {
            var r, l;
            return (l = (r = n.slots).default) === null || l === void 0
              ? void 0
              : l.call(r, t());
          },
        }
    : n.slots.default;
function ze(e) {
  if (bn(e)) return e._value;
}
function bn(e) {
  return "_value" in e;
}
function Jn(e) {
  return e.type === "number"
    ? Number.isNaN(e.valueAsNumber)
      ? e.value
      : e.valueAsNumber
    : e.value;
}
function Pe(e) {
  if (!Ke(e)) return e;
  const n = e.target;
  if (je(n.type) && bn(n)) return ze(n);
  if (n.type === "file" && n.files) {
    const t = Array.from(n.files);
    return n.multiple ? t : t[0];
  }
  if (Wn(n))
    return Array.from(n.options)
      .filter((t) => t.selected && !t.disabled)
      .map(ze);
  if (hn(n)) {
    const t = Array.from(n.options).find((r) => r.selected);
    return t ? ze(t) : n.value;
  }
  return Jn(n);
}
function On(e) {
  const n = {};
  return (
    Object.defineProperty(n, "_$$isNormalized", {
      value: !0,
      writable: !1,
      enumerable: !1,
      configurable: !1,
    }),
    e
      ? ge(e) && e._$$isNormalized
        ? e
        : ge(e)
        ? Object.keys(e).reduce((t, r) => {
            const l = Xn(e[r]);
            return e[r] !== !1 && (t[r] = an(l)), t;
          }, n)
        : typeof e != "string"
        ? n
        : e.split("|").reduce((t, r) => {
            const l = Qn(r);
            return l.name && (t[l.name] = an(l.params)), t;
          }, n)
      : n
  );
}
function Xn(e) {
  return e === !0 ? [] : Array.isArray(e) || ge(e) ? e : [e];
}
function an(e) {
  const n = (t) => (typeof t == "string" && t[0] === "@" ? Zn(t.slice(1)) : t);
  return Array.isArray(e)
    ? e.map(n)
    : e instanceof RegExp
    ? [e]
    : Object.keys(e).reduce((t, r) => ((t[r] = n(e[r])), t), {});
}
const Qn = (e) => {
  let n = [];
  const t = e.split(":")[0];
  return (
    e.includes(":") && (n = e.split(":").slice(1).join(":").split(",")),
    { name: t, params: n }
  );
};
function Zn(e) {
  const n = (t) => Y(t, e) || t[e];
  return (n.__locatorRef = e), n;
}
function xn(e) {
  return Array.isArray(e)
    ? e.filter(De)
    : le(e)
        .filter((n) => De(e[n]))
        .map((n) => e[n]);
}
const et = {
  generateMessage: ({ field: e }) => `${e} is not valid.`,
  bails: !0,
  validateOnBlur: !0,
  validateOnChange: !0,
  validateOnInput: !1,
  validateOnModelUpdate: !0,
};
let nt = Object.assign({}, et);
const ce = () => nt;
async function Vn(e, n, t = {}) {
  const r = t == null ? void 0 : t.bails,
    l = {
      name: (t == null ? void 0 : t.name) || "{field}",
      rules: n,
      label: t == null ? void 0 : t.label,
      bails: r ?? !0,
      formData: (t == null ? void 0 : t.values) || {},
    },
    s = (await tt(l, e)).errors;
  return { errors: s, valid: !s.length };
}
async function tt(e, n) {
  if (ve(e.rules) || Ie(e.rules)) return it(n, e.rules);
  if (D(e.rules) || Array.isArray(e.rules)) {
    const s = {
        field: e.label || e.name,
        name: e.name,
        label: e.label,
        form: e.formData,
        value: n,
      },
      h = Array.isArray(e.rules) ? e.rules : [e.rules],
      d = h.length,
      c = [];
    for (let y = 0; y < d; y++) {
      const b = h[y],
        S = await b(n, s);
      if (!(typeof S != "string" && !Array.isArray(S) && S)) {
        if (Array.isArray(S)) c.push(...S);
        else {
          const I = typeof S == "string" ? S : Sn(s);
          c.push(I);
        }
        if (e.bails) return { errors: c };
      }
    }
    return { errors: c };
  }
  const t = Object.assign(Object.assign({}, e), { rules: On(e.rules) }),
    r = [],
    l = Object.keys(t.rules),
    u = l.length;
  for (let s = 0; s < u; s++) {
    const h = l[s],
      d = await at(t, n, { name: h, params: t.rules[h] });
    if (d.error && (r.push(d.error), e.bails)) return { errors: r };
  }
  return { errors: r };
}
function rt(e) {
  return !!e && e.name === "ValidationError";
}
function pn(e) {
  return {
    __type: "VVTypedSchema",
    async parse(t) {
      var r;
      try {
        return { output: await e.validate(t, { abortEarly: !1 }), errors: [] };
      } catch (l) {
        if (!rt(l)) throw l;
        if (
          !(!((r = l.inner) === null || r === void 0) && r.length) &&
          l.errors.length
        )
          return { errors: [{ path: l.path, errors: l.errors }] };
        const u = l.inner.reduce((s, h) => {
          const d = h.path || "";
          return (
            s[d] || (s[d] = { errors: [], path: d }),
            s[d].errors.push(...h.errors),
            s
          );
        }, {});
        return { errors: Object.values(u) };
      }
    },
  };
}
async function it(e, n) {
  const r = await (ve(n) ? n : pn(n)).parse(e),
    l = [];
  for (const u of r.errors) u.errors.length && l.push(...u.errors);
  return { errors: l };
}
async function at(e, n, t) {
  const r = zn(t.name);
  if (!r) throw new Error(`No such validator '${t.name}' exists.`);
  const l = lt(t.params, e.formData),
    u = {
      field: e.label || e.name,
      name: e.name,
      label: e.label,
      value: n,
      form: e.formData,
      rule: Object.assign(Object.assign({}, t), { params: l }),
    },
    s = await r(n, l, u);
  return typeof s == "string" ? { error: s } : { error: s ? void 0 : Sn(u) };
}
function Sn(e) {
  const n = ce().generateMessage;
  return n ? n(e) : "Field is invalid";
}
function lt(e, n) {
  const t = (r) => (De(r) ? r(n) : r);
  return Array.isArray(e)
    ? e.map(t)
    : Object.keys(e).reduce((r, l) => ((r[l] = t(e[l])), r), {});
}
async function ut(e, n) {
  const r = await (ve(e) ? e : pn(e)).parse(n),
    l = {},
    u = {};
  for (const s of r.errors) {
    const h = s.errors,
      d = (s.path || "").replace(/\["(\d+)"\]/g, (c, y) => `[${y}]`);
    (l[d] = { valid: !h.length, errors: h }), h.length && (u[d] = h[0]);
  }
  return { valid: !r.errors.length, results: l, errors: u, values: r.value };
}
async function ot(e, n, t) {
  const l = le(e).map(async (c) => {
    var y, b, S;
    const g =
        (y = t == null ? void 0 : t.names) === null || y === void 0
          ? void 0
          : y[c],
      I = await Vn(Y(n, c), e[c], {
        name: (g == null ? void 0 : g.name) || c,
        label: g == null ? void 0 : g.label,
        values: n,
        bails:
          (S =
            (b = t == null ? void 0 : t.bailsMap) === null || b === void 0
              ? void 0
              : b[c]) !== null && S !== void 0
            ? S
            : !0,
      });
    return Object.assign(Object.assign({}, I), { path: c });
  });
  let u = !0;
  const s = await Promise.all(l),
    h = {},
    d = {};
  for (const c of s)
    (h[c.path] = { valid: c.valid, errors: c.errors }),
      c.valid || ((u = !1), (d[c.path] = c.errors[0]));
  return { valid: u, results: h, errors: d };
}
let ln = 0;
function st(e, n) {
  const {
    value: t,
    initialValue: r,
    setInitialValue: l,
  } = dt(e, n.modelValue, n.form);
  if (!n.form) {
    let S = function (g) {
      var I;
      "value" in g && (t.value = g.value),
        "errors" in g && c(g.errors),
        "touched" in g &&
          (b.touched =
            (I = g.touched) !== null && I !== void 0 ? I : b.touched),
        "initialValue" in g && l(g.initialValue);
    };
    const { errors: d, setErrors: c } = vt(),
      y = ln >= Number.MAX_SAFE_INTEGER ? 0 : ++ln,
      b = ft(t, r, d);
    return {
      id: y,
      path: e,
      value: t,
      initialValue: r,
      meta: b,
      flags: { pendingUnmount: { [y]: !1 } },
      errors: d,
      setState: S,
    };
  }
  const u = n.form.createPathState(e, {
      bails: n.bails,
      label: n.label,
      type: n.type,
      validate: n.validate,
    }),
    s = M(() => u.errors);
  function h(d) {
    var c, y, b;
    "value" in d && (t.value = d.value),
      "errors" in d &&
        ((c = n.form) === null ||
          c === void 0 ||
          c.setFieldError(m(e), d.errors)),
      "touched" in d &&
        ((y = n.form) === null ||
          y === void 0 ||
          y.setFieldTouched(
            m(e),
            (b = d.touched) !== null && b !== void 0 ? b : !1
          )),
      "initialValue" in d && l(d.initialValue);
  }
  return {
    id: Array.isArray(u.id) ? u.id[u.id.length - 1] : u.id,
    path: e,
    value: t,
    errors: s,
    meta: u,
    initialValue: r,
    flags: u.__flags,
    setState: h,
  };
}
function dt(e, n, t) {
  const r = ae(m(n));
  function l() {
    return t ? Y(t.initialValues.value, m(e), m(r)) : m(r);
  }
  function u(c) {
    if (!t) {
      r.value = c;
      return;
    }
    t.stageInitialValue(m(e), c, !0);
  }
  const s = M(l);
  if (!t) return { value: ae(l()), initialValue: s, setInitialValue: u };
  const h = ct(n, t, s, e);
  return (
    t.stageInitialValue(m(e), h, !0),
    {
      value: M({
        get() {
          return Y(t.values, m(e));
        },
        set(c) {
          t.setFieldValue(m(e), c, !1);
        },
      }),
      initialValue: s,
      setInitialValue: u,
    }
  );
}
function ct(e, n, t, r) {
  return Ae(e) ? m(e) : e !== void 0 ? e : Y(n.values, m(r), m(t));
}
function ft(e, n, t) {
  const r = Fe({
    touched: !1,
    pending: !1,
    valid: !0,
    validated: !!m(t).length,
    initialValue: M(() => m(n)),
    dirty: M(() => !W(m(e), m(n))),
  });
  return (
    fe(
      t,
      (l) => {
        r.valid = !l.length;
      },
      { immediate: !0, flush: "sync" }
    ),
    r
  );
}
function vt() {
  const e = ae([]);
  return {
    errors: e,
    setErrors: (n) => {
      e.value = $e(n);
    },
  };
}
function ht(e, n, t) {
  return je(t == null ? void 0 : t.type) ? yt(e, n, t) : _n(e, n, t);
}
function _n(e, n, t) {
  const {
      initialValue: r,
      validateOnMount: l,
      bails: u,
      type: s,
      checkedValue: h,
      label: d,
      validateOnValueUpdate: c,
      uncheckedValue: y,
      controlled: b,
      keepValueOnUnmount: S,
      syncVModel: g,
      form: I,
    } = mt(t),
    q = b ? yn(Ge) : void 0,
    O = I || q,
    R = M(() => _e(ie(e))),
    N = M(() => {
      if (m(O == null ? void 0 : O.schema)) return;
      const V = m(n);
      return Ie(V) || ve(V) || D(V) || Array.isArray(V) ? V : On(V);
    }),
    {
      id: L,
      value: J,
      initialValue: X,
      meta: k,
      setState: ne,
      errors: T,
      flags: U,
    } = st(R, {
      modelValue: r,
      form: O,
      bails: u,
      label: d,
      type: s,
      validate: N.value ? K : void 0,
    }),
    z = M(() => T.value[0]);
  g && gt({ value: J, prop: g, handleChange: P });
  const te = (f, V = !1) => {
    (k.touched = !0), V && Q();
  };
  async function ue(f) {
    var V, C;
    return O != null && O.validateSchema
      ? (V = (await O.validateSchema(f)).results[m(R)]) !== null && V !== void 0
        ? V
        : { valid: !0, errors: [] }
      : N.value
      ? Vn(J.value, N.value, {
          name: m(R),
          label: m(d),
          values:
            (C = O == null ? void 0 : O.values) !== null && C !== void 0
              ? C
              : {},
          bails: u,
        })
      : { valid: !0, errors: [] };
  }
  const Q = Le(
      async () => ((k.pending = !0), (k.validated = !0), ue("validated-only")),
      (f) => {
        if (!U.pendingUnmount[H.id])
          return (
            ne({ errors: f.errors }), (k.pending = !1), (k.valid = f.valid), f
          );
      }
    ),
    G = Le(
      async () => ue("silent"),
      (f) => ((k.valid = f.valid), f)
    );
  function K(f) {
    return (f == null ? void 0 : f.mode) === "silent" ? G() : Q();
  }
  function P(f, V = !0) {
    const C = Pe(f);
    he(C, V);
  }
  cn(() => {
    if (l) return Q();
    (!O || !O.validateSchema) && G();
  });
  function x(f) {
    k.touched = f;
  }
  function $(f) {
    var V;
    const C = f && "value" in f ? f.value : X.value;
    ne({
      value: w(C),
      initialValue: w(C),
      touched:
        (V = f == null ? void 0 : f.touched) !== null && V !== void 0 ? V : !1,
      errors: (f == null ? void 0 : f.errors) || [],
    }),
      (k.pending = !1),
      (k.validated = !1),
      G();
  }
  const re = we();
  function he(f, V = !0) {
    (J.value = re && g ? Yn(f, re.props.modelModifiers) : f), (V ? Q : G)();
  }
  function Ee(f) {
    ne({ errors: Array.isArray(f) ? f : [f] });
  }
  const qe = M({
      get() {
        return J.value;
      },
      set(f) {
        he(f, c);
      },
    }),
    H = {
      id: L,
      name: R,
      label: d,
      value: qe,
      meta: k,
      errors: T,
      errorMessage: z,
      type: s,
      checkedValue: h,
      uncheckedValue: y,
      bails: u,
      keepValueOnUnmount: S,
      resetField: $,
      handleReset: () => $(),
      validate: K,
      handleChange: P,
      handleBlur: te,
      setState: ne,
      setTouched: x,
      setErrors: Ee,
      setValue: he,
    };
  if (
    (fn(Dn, H),
    Ae(n) &&
      typeof m(n) != "function" &&
      fe(
        n,
        (f, V) => {
          W(f, V) || (k.validated ? Q() : G());
        },
        { deep: !0 }
      ),
    !O)
  )
    return H;
  const ke = M(() => {
    const f = N.value;
    return !f || D(f) || Ie(f) || ve(f) || Array.isArray(f)
      ? {}
      : Object.keys(f).reduce((V, C) => {
          const E = xn(f[C])
            .map((oe) => oe.__locatorRef)
            .reduce((oe, se) => {
              const ee = Y(O.values, se) || O.values[se];
              return ee !== void 0 && (oe[se] = ee), oe;
            }, {});
          return Object.assign(V, E), V;
        }, {});
  });
  return (
    fe(ke, (f, V) => {
      if (!Object.keys(f).length) return;
      !W(f, V) && (k.validated ? Q() : G());
    }),
    Cn(() => {
      var f;
      const V =
          (f = m(H.keepValueOnUnmount)) !== null && f !== void 0
            ? f
            : m(O.keepValuesOnUnmount),
        C = ie(R);
      if (V || !O || U.pendingUnmount[H.id]) {
        O == null || O.removePathState(C, L);
        return;
      }
      U.pendingUnmount[H.id] = !0;
      const E = O.getPathState(C);
      if (
        Array.isArray(E == null ? void 0 : E.id) && E != null && E.multiple
          ? E != null && E.id.includes(H.id)
          : (E == null ? void 0 : E.id) === H.id
      ) {
        if (E != null && E.multiple && Array.isArray(E.value)) {
          const se = E.value.findIndex((ee) => W(ee, m(H.checkedValue)));
          if (se > -1) {
            const ee = [...E.value];
            ee.splice(se, 1), O.setFieldValue(C, ee);
          }
          Array.isArray(E.id) && E.id.splice(E.id.indexOf(H.id), 1);
        } else O.unsetPathValue(ie(R));
        O.removePathState(C, L);
      }
    }),
    H
  );
}
function mt(e) {
  const n = () => ({
      initialValue: void 0,
      validateOnMount: !1,
      bails: !0,
      label: void 0,
      validateOnValueUpdate: !0,
      keepValueOnUnmount: void 0,
      syncVModel: !1,
      controlled: !0,
    }),
    t = !!(e != null && e.syncVModel),
    r =
      typeof (e == null ? void 0 : e.syncVModel) == "string"
        ? e.syncVModel
        : (e == null ? void 0 : e.modelPropName) || "modelValue",
    l =
      t && !("initialValue" in (e || {}))
        ? He(we(), r)
        : e == null
        ? void 0
        : e.initialValue;
  if (!e) return Object.assign(Object.assign({}, n()), { initialValue: l });
  const u = "valueProp" in e ? e.valueProp : e.checkedValue,
    s = "standalone" in e ? !e.standalone : e.controlled,
    h =
      (e == null ? void 0 : e.modelPropName) ||
      (e == null ? void 0 : e.syncVModel) ||
      !1;
  return Object.assign(Object.assign(Object.assign({}, n()), e || {}), {
    initialValue: l,
    controlled: s ?? !0,
    checkedValue: u,
    syncVModel: h,
  });
}
function yt(e, n, t) {
  const r = t != null && t.standalone ? void 0 : yn(Ge),
    l = t == null ? void 0 : t.checkedValue,
    u = t == null ? void 0 : t.uncheckedValue;
  function s(h) {
    const d = h.handleChange,
      c = M(() => {
        const b = m(h.value),
          S = m(l);
        return Array.isArray(b) ? b.findIndex((g) => W(g, S)) >= 0 : W(S, b);
      });
    function y(b, S = !0) {
      var g, I;
      if (
        c.value ===
        ((g = b == null ? void 0 : b.target) === null || g === void 0
          ? void 0
          : g.checked)
      ) {
        S && h.validate();
        return;
      }
      const q = ie(e),
        O = r == null ? void 0 : r.getPathState(q),
        R = Pe(b);
      let N = (I = m(l)) !== null && I !== void 0 ? I : R;
      r && O != null && O.multiple && O.type === "checkbox"
        ? (N = tn(Y(r.values, q) || [], N, void 0))
        : (t == null ? void 0 : t.type) === "checkbox" &&
          (N = tn(m(h.value), N, m(u))),
        d(N, S);
    }
    return Object.assign(Object.assign({}, h), {
      checked: c,
      checkedValue: l,
      uncheckedValue: u,
      handleChange: y,
    });
  }
  return s(_n(e, n, t));
}
function gt({ prop: e, value: n, handleChange: t }) {
  const r = we();
  if (!r || !e) return;
  const l = typeof e == "string" ? e : "modelValue",
    u = `update:${l}`;
  l in r.props &&
    (fe(n, (s) => {
      W(s, He(r, l)) || r.emit(u, s);
    }),
    fe(
      () => He(r, l),
      (s) => {
        if (s === Ce && n.value === void 0) return;
        const h = s === Ce ? void 0 : s;
        W(h, n.value) || t(h);
      }
    ));
}
function He(e, n) {
  if (e) return e.props[n];
}
const bt = on({
  name: "Field",
  inheritAttrs: !1,
  props: {
    as: { type: [String, Object], default: void 0 },
    name: { type: String, required: !0 },
    rules: { type: [Object, String, Function], default: void 0 },
    validateOnMount: { type: Boolean, default: !1 },
    validateOnBlur: { type: Boolean, default: void 0 },
    validateOnChange: { type: Boolean, default: void 0 },
    validateOnInput: { type: Boolean, default: void 0 },
    validateOnModelUpdate: { type: Boolean, default: void 0 },
    bails: { type: Boolean, default: () => ce().bails },
    label: { type: String, default: void 0 },
    uncheckedValue: { type: null, default: void 0 },
    modelValue: { type: null, default: Ce },
    modelModifiers: { type: null, default: () => ({}) },
    "onUpdate:modelValue": { type: null, default: void 0 },
    standalone: { type: Boolean, default: !1 },
    keepValue: { type: Boolean, default: void 0 },
  },
  setup(e, n) {
    const t = de(e, "rules"),
      r = de(e, "name"),
      l = de(e, "label"),
      u = de(e, "uncheckedValue"),
      s = de(e, "keepValue"),
      {
        errors: h,
        value: d,
        errorMessage: c,
        validate: y,
        handleChange: b,
        handleBlur: S,
        setTouched: g,
        resetField: I,
        handleReset: q,
        meta: O,
        checked: R,
        setErrors: N,
      } = ht(r, t, {
        validateOnMount: e.validateOnMount,
        bails: e.bails,
        standalone: e.standalone,
        type: n.attrs.type,
        initialValue: Vt(e, n),
        checkedValue: n.attrs.value,
        uncheckedValue: u,
        label: l,
        validateOnValueUpdate: !1,
        keepValueOnUnmount: s,
        syncVModel: !0,
      }),
      L = function (U, z = !0) {
        b(U, z), n.emit("update:modelValue", d.value);
      },
      J = M(() => {
        const {
          validateOnInput: T,
          validateOnChange: U,
          validateOnBlur: z,
          validateOnModelUpdate: te,
        } = Ot(e);
        function ue(P) {
          S(P, z), D(n.attrs.onBlur) && n.attrs.onBlur(P);
        }
        function Q(P) {
          L(P, T), D(n.attrs.onInput) && n.attrs.onInput(P);
        }
        function G(P) {
          L(P, U), D(n.attrs.onChange) && n.attrs.onChange(P);
        }
        const K = { name: e.name, onBlur: ue, onInput: Q, onChange: G };
        return (K["onUpdate:modelValue"] = (P) => L(P, te)), K;
      }),
      X = M(() => {
        const T = Object.assign({}, J.value);
        je(n.attrs.type) && R && (T.checked = R.value);
        const U = un(e, n);
        return Kn(U, n.attrs) && (T.value = d.value), T;
      }),
      k = M(() =>
        Object.assign(Object.assign({}, J.value), { modelValue: d.value })
      );
    function ne() {
      return {
        field: X.value,
        componentField: k.value,
        value: d.value,
        meta: O,
        errors: h.value,
        errorMessage: c.value,
        validate: y,
        resetField: I,
        handleChange: L,
        handleInput: (T) => L(T, !1),
        handleReset: q,
        handleBlur: J.value.onBlur,
        setTouched: g,
        setErrors: N,
      };
    }
    return (
      n.expose({
        setErrors: N,
        setTouched: g,
        reset: I,
        validate: y,
        handleChange: b,
      }),
      () => {
        const T = sn(un(e, n)),
          U = gn(T, n, ne);
        return T
          ? dn(T, Object.assign(Object.assign({}, n.attrs), X.value), U)
          : U;
      }
    );
  },
});
function un(e, n) {
  let t = e.as || "";
  return !e.as && !n.slots.default && (t = "input"), t;
}
function Ot(e) {
  var n, t, r, l;
  const {
    validateOnInput: u,
    validateOnChange: s,
    validateOnBlur: h,
    validateOnModelUpdate: d,
  } = ce();
  return {
    validateOnInput: (n = e.validateOnInput) !== null && n !== void 0 ? n : u,
    validateOnChange: (t = e.validateOnChange) !== null && t !== void 0 ? t : s,
    validateOnBlur: (r = e.validateOnBlur) !== null && r !== void 0 ? r : h,
    validateOnModelUpdate:
      (l = e.validateOnModelUpdate) !== null && l !== void 0 ? l : d,
  };
}
function Vt(e, n) {
  return je(n.attrs.type)
    ? Ze(e, "modelValue")
      ? e.modelValue
      : void 0
    : Ze(e, "modelValue")
    ? e.modelValue
    : n.attrs.value;
}
const Ft = bt;
let pt = 0;
const Se = ["bails", "fieldsCount", "id", "multiple", "type", "validate"];
function An(e) {
  const n = m(e == null ? void 0 : e.initialValues) || {},
    t = m(e == null ? void 0 : e.validationSchema);
  return t && ve(t) && D(t.cast) ? w(t.cast(n) || {}) : w(n);
}
function St(e) {
  var n;
  const t = pt++;
  let r = 0;
  const l = ae(!1),
    u = ae(!1),
    s = ae(0),
    h = [],
    d = Fe(An(e)),
    c = ae([]),
    y = ae({}),
    b = ae({}),
    S = qn(() => {
      b.value = c.value.reduce((a, i) => ((a[_e(ie(i.path))] = i), a), {});
    });
  function g(a, i) {
    const o = P(a);
    if (!o) {
      typeof a == "string" && (y.value[_e(a)] = $e(i));
      return;
    }
    if (typeof a == "string") {
      const v = _e(a);
      y.value[v] && delete y.value[v];
    }
    (o.errors = $e(i)), (o.valid = !o.errors.length);
  }
  function I(a) {
    le(a).forEach((i) => {
      g(i, a[i]);
    });
  }
  e != null && e.initialErrors && I(e.initialErrors);
  const q = M(() => {
      const a = c.value.reduce(
        (i, o) => (o.errors.length && (i[o.path] = o.errors), i),
        {}
      );
      return Object.assign(Object.assign({}, y.value), a);
    }),
    O = M(() =>
      le(q.value).reduce((a, i) => {
        const o = q.value[i];
        return o != null && o.length && (a[i] = o[0]), a;
      }, {})
    ),
    R = M(() =>
      c.value.reduce(
        (a, i) => (
          (a[i.path] = { name: i.path || "", label: i.label || "" }), a
        ),
        {}
      )
    ),
    N = M(() =>
      c.value.reduce((a, i) => {
        var o;
        return (a[i.path] = (o = i.bails) !== null && o !== void 0 ? o : !0), a;
      }, {})
    ),
    L = Object.assign({}, (e == null ? void 0 : e.initialErrors) || {}),
    J =
      (n = e == null ? void 0 : e.keepValuesOnUnmount) !== null && n !== void 0
        ? n
        : !1,
    {
      initialValues: X,
      originalInitialValues: k,
      setInitialValues: ne,
    } = At(c, d, e),
    T = _t(c, d, k, O),
    U = M(() =>
      c.value.reduce((a, i) => {
        const o = Y(d, i.path);
        return ye(a, i.path, o), a;
      }, {})
    ),
    z = e == null ? void 0 : e.validationSchema;
  function te(a, i) {
    var o, v;
    const _ = M(() => Y(X.value, ie(a))),
      p = b.value[ie(a)];
    if (p) {
      ((i == null ? void 0 : i.type) === "checkbox" ||
        (i == null ? void 0 : i.type) === "radio") &&
        (p.multiple = !0);
      const Z = r++;
      return (
        Array.isArray(p.id) ? p.id.push(Z) : (p.id = [p.id, Z]),
        p.fieldsCount++,
        (p.__flags.pendingUnmount[Z] = !1),
        p
      );
    }
    const A = M(() => Y(d, ie(a))),
      B = ie(a),
      j = r++,
      F = Fe({
        id: j,
        path: a,
        touched: !1,
        pending: !1,
        valid: !0,
        validated: !!(!((o = L[B]) === null || o === void 0) && o.length),
        initialValue: _,
        errors: Bn([]),
        bails:
          (v = i == null ? void 0 : i.bails) !== null && v !== void 0 ? v : !1,
        label: i == null ? void 0 : i.label,
        type: (i == null ? void 0 : i.type) || "default",
        value: A,
        multiple: !1,
        __flags: { pendingUnmount: { [j]: !1 } },
        fieldsCount: 1,
        validate: i == null ? void 0 : i.validate,
        dirty: M(() => !W(m(A), m(_))),
      });
    return (
      c.value.push(F),
      (b.value[B] = F),
      S(),
      O.value[B] &&
        !L[B] &&
        be(() => {
          me(B, { mode: "silent" });
        }),
      Ae(a) &&
        fe(a, (Z) => {
          S();
          const Ve = w(A.value);
          (b.value[Z] = F),
            be(() => {
              ye(d, Z, Ve);
            });
        }),
      F
    );
  }
  const ue = rn(Qe, 5),
    Q = rn(Qe, 5),
    G = Le(
      async (a) => ((await a) === "silent" ? ue() : Q()),
      (a, [i]) => {
        const o = le(V.errorBag.value);
        return [
          ...new Set([...le(a.results), ...c.value.map((_) => _.path), ...o]),
        ]
          .sort()
          .reduce(
            (_, p) => {
              const A = p,
                B = P(A) || x(A),
                j = (a.results[A] || { errors: [] }).errors,
                F = { errors: j, valid: !j.length };
              return (
                (_.results[A] = F),
                F.valid || (_.errors[A] = F.errors[0]),
                B && y.value[A] && delete y.value[A],
                B
                  ? ((B.valid = F.valid),
                    i === "silent" ||
                      (i === "validated-only" && !B.validated) ||
                      g(B, F.errors),
                    _)
                  : (g(A, j), _)
              );
            },
            { valid: a.valid, results: {}, errors: {} }
          );
      }
    );
  function K(a) {
    c.value.forEach(a);
  }
  function P(a) {
    const i = typeof a == "string" ? _e(a) : a;
    return typeof i == "string" ? b.value[i] : i;
  }
  function x(a) {
    return c.value
      .filter((o) => a.startsWith(o.path))
      .reduce(
        (o, v) => (o ? (v.path.length > o.path.length ? v : o) : v),
        void 0
      );
  }
  let $ = [],
    re;
  function he(a) {
    return (
      $.push(a),
      re ||
        (re = be(() => {
          [...$]
            .sort()
            .reverse()
            .forEach((o) => {
              nn(d, o);
            }),
            ($ = []),
            (re = null);
        })),
      re
    );
  }
  function Ee(a) {
    return function (o, v) {
      return function (p) {
        return (
          p instanceof Event && (p.preventDefault(), p.stopPropagation()),
          K((A) => (A.touched = !0)),
          (l.value = !0),
          s.value++,
          Oe()
            .then((A) => {
              const B = w(d);
              if (A.valid && typeof o == "function") {
                const j = w(U.value);
                let F = a ? j : B;
                return (
                  A.values && (F = A.values),
                  o(F, {
                    evt: p,
                    controlledValues: j,
                    setErrors: I,
                    setFieldError: g,
                    setTouched: Te,
                    setFieldTouched: ee,
                    setValues: E,
                    setFieldValue: C,
                    resetForm: Ne,
                    resetField: Je,
                  })
                );
              }
              !A.valid &&
                typeof v == "function" &&
                v({ values: B, evt: p, errors: A.errors, results: A.results });
            })
            .then(
              (A) => ((l.value = !1), A),
              (A) => {
                throw ((l.value = !1), A);
              }
            )
        );
      };
    };
  }
  const H = Ee(!1);
  H.withControlled = Ee(!0);
  function ke(a, i) {
    const o = c.value.findIndex((_) => _.path === a),
      v = c.value[o];
    if (!(o === -1 || !v)) {
      if (
        (be(() => {
          me(a, { mode: "silent", warn: !1 });
        }),
        v.multiple && v.fieldsCount && v.fieldsCount--,
        Array.isArray(v.id))
      ) {
        const _ = v.id.indexOf(i);
        _ >= 0 && v.id.splice(_, 1), delete v.__flags.pendingUnmount[i];
      }
      (!v.multiple || v.fieldsCount <= 0) &&
        (c.value.splice(o, 1), Xe(a), S(), delete b.value[a]);
    }
  }
  function f(a) {
    return K((i) => {
      i.path.startsWith(a) &&
        le(i.__flags.pendingUnmount).forEach((o) => {
          i.__flags.pendingUnmount[o] = !0;
        });
    });
  }
  const V = {
    formId: t,
    values: d,
    controlledValues: U,
    errorBag: q,
    errors: O,
    schema: z,
    submitCount: s,
    meta: T,
    isSubmitting: l,
    isValidating: u,
    fieldArrays: h,
    keepValuesOnUnmount: J,
    validateSchema: m(z) ? G : void 0,
    validate: Oe,
    setFieldError: g,
    validateField: me,
    setFieldValue: C,
    setValues: E,
    setErrors: I,
    setFieldTouched: ee,
    setTouched: Te,
    resetForm: Ne,
    resetField: Je,
    handleSubmit: H,
    stageInitialValue: jn,
    unsetInitialValue: Xe,
    setFieldInitialValue: Ue,
    useFieldModel: se,
    createPathState: te,
    getPathState: P,
    unsetPathValue: he,
    removePathState: ke,
    initialValues: X,
    getAllPathStates: () => c.value,
    markForUnmount: f,
  };
  function C(a, i, o = !0) {
    const v = w(i),
      _ = typeof a == "string" ? a : a.path;
    P(_) || te(_), ye(d, _, v), o && me(_);
  }
  function E(a, i = !0) {
    Me(d, a), h.forEach((o) => o && o.reset()), i && Oe();
  }
  function oe(a) {
    const i = P(m(a)) || te(a);
    return M({
      get() {
        return i.value;
      },
      set(o) {
        const v = m(a);
        C(v, o, !1),
          (i.validated = !0),
          (i.pending = !0),
          me(v).then(() => {
            i.pending = !1;
          });
      },
    });
  }
  function se(a) {
    return Array.isArray(a) ? a.map(oe) : oe(a);
  }
  function ee(a, i) {
    const o = P(a);
    o && (o.touched = i);
  }
  function Te(a) {
    if (typeof a == "boolean") {
      K((i) => {
        i.touched = a;
      });
      return;
    }
    le(a).forEach((i) => {
      ee(i, !!a[i]);
    });
  }
  function Je(a, i) {
    var o;
    const v = i && "value" in i ? i.value : Y(X.value, a);
    Ue(a, w(v)),
      C(a, v, !1),
      ee(
        a,
        (o = i == null ? void 0 : i.touched) !== null && o !== void 0 ? o : !1
      ),
      g(a, (i == null ? void 0 : i.errors) || []);
  }
  function Ne(a) {
    let i = a != null && a.values ? a.values : k.value;
    (i = ve(z) && D(z.cast) ? z.cast(i) : i),
      ne(i),
      K((o) => {
        var v;
        (o.validated = !1),
          (o.touched =
            ((v = a == null ? void 0 : a.touched) === null || v === void 0
              ? void 0
              : v[o.path]) || !1),
          C(o.path, Y(i, o.path), !1),
          g(o.path, void 0);
      }),
      E(i, !1),
      I((a == null ? void 0 : a.errors) || {}),
      (s.value = (a == null ? void 0 : a.submitCount) || 0),
      be(() => {
        Oe({ mode: "silent" });
      });
  }
  async function Oe(a) {
    const i = (a == null ? void 0 : a.mode) || "force";
    if ((i === "force" && K((p) => (p.validated = !0)), V.validateSchema))
      return V.validateSchema(i);
    u.value = !0;
    const o = await Promise.all(
      c.value.map((p) =>
        p.validate
          ? p
              .validate(a)
              .then((A) => ({ key: p.path, valid: A.valid, errors: A.errors }))
          : Promise.resolve({ key: p.path, valid: !0, errors: [] })
      )
    );
    u.value = !1;
    const v = {},
      _ = {};
    for (const p of o)
      (v[p.key] = { valid: p.valid, errors: p.errors }),
        p.errors.length && (_[p.key] = p.errors[0]);
    return { valid: o.every((p) => p.valid), results: v, errors: _ };
  }
  async function me(a, i) {
    var o;
    const v = P(a);
    if ((v && (v.validated = !0), z)) {
      const { results: _ } = await G(
        (i == null ? void 0 : i.mode) || "validated-only"
      );
      return _[a] || { errors: [], valid: !0 };
    }
    return v != null && v.validate
      ? v.validate(i)
      : (!v && (o = i == null ? void 0 : i.warn),
        Promise.resolve({ errors: [], valid: !0 }));
  }
  function Xe(a) {
    nn(X.value, a);
  }
  function jn(a, i, o = !1) {
    Ue(a, i),
      ye(d, a, i),
      o && !(e != null && e.initialValues) && ye(k.value, a, w(i));
  }
  function Ue(a, i) {
    ye(X.value, a, w(i));
  }
  async function Qe() {
    const a = m(z);
    if (!a) return { valid: !0, results: {}, errors: {} };
    u.value = !0;
    const i =
      Ie(a) || ve(a)
        ? await ut(a, d)
        : await ot(a, d, { names: R.value, bailsMap: N.value });
    return (u.value = !1), i;
  }
  const En = H((a, { evt: i }) => {
    mn(i) && i.target.submit();
  });
  cn(() => {
    if (
      (e != null && e.initialErrors && I(e.initialErrors),
      e != null && e.initialTouched && Te(e.initialTouched),
      e != null && e.validateOnMount)
    ) {
      Oe();
      return;
    }
    V.validateSchema && V.validateSchema("silent");
  }),
    Ae(z) &&
      fe(z, () => {
        var a;
        (a = V.validateSchema) === null ||
          a === void 0 ||
          a.call(V, "validated-only");
      }),
    fn(Ge, V);
  function Fn(a, i) {
    const o = P(ie(a)) || te(a),
      v = () => (D(i) ? i(pe(o, Se)) : i || {});
    function _() {
      var B;
      (o.touched = !0),
        ((B = v().validateOnBlur) !== null && B !== void 0
          ? B
          : ce().validateOnBlur) && me(o.path);
    }
    function p(B) {
      var j;
      const F =
        (j = v().validateOnModelUpdate) !== null && j !== void 0
          ? j
          : ce().validateOnModelUpdate;
      C(o.path, B, F);
    }
    return M(() => {
      if (D(i)) {
        const F = i(o),
          Z = F.model || "modelValue";
        return Object.assign(
          { onBlur: _, [Z]: o.value, [`onUpdate:${Z}`]: p },
          F.props || {}
        );
      }
      const B = (i == null ? void 0 : i.model) || "modelValue",
        j = { onBlur: _, [B]: o.value, [`onUpdate:${B}`]: p };
      return i != null && i.mapProps
        ? Object.assign(Object.assign({}, j), i.mapProps(pe(o, Se)))
        : j;
    });
  }
  function Mn(a, i) {
    const o = P(ie(a)) || te(a),
      v = () => (D(i) ? i(pe(o, Se)) : i || {});
    function _() {
      var j;
      (o.touched = !0),
        ((j = v().validateOnBlur) !== null && j !== void 0
          ? j
          : ce().validateOnBlur) && me(o.path);
    }
    function p(j) {
      var F;
      const Z = Pe(j),
        Ve =
          (F = v().validateOnInput) !== null && F !== void 0
            ? F
            : ce().validateOnInput;
      C(o.path, Z, Ve);
    }
    function A(j) {
      var F;
      const Z = Pe(j),
        Ve =
          (F = v().validateOnChange) !== null && F !== void 0
            ? F
            : ce().validateOnChange;
      C(o.path, Z, Ve);
    }
    return M(() => {
      const j = { value: o.value, onChange: A, onInput: p, onBlur: _ };
      return D(i)
        ? Object.assign(Object.assign({}, j), i(pe(o, Se)).attrs || {})
        : i != null && i.mapAttrs
        ? Object.assign(Object.assign({}, j), i.mapAttrs(pe(o, Se)))
        : j;
    });
  }
  return Object.assign(Object.assign({}, V), {
    values: In(d),
    handleReset: () => Ne(),
    submitForm: En,
    defineComponentBinds: Fn,
    defineInputBinds: Mn,
  });
}
function _t(e, n, t, r) {
  const l = { touched: "some", pending: "some", valid: "every" },
    u = M(() => !W(n, m(t)));
  function s() {
    const d = e.value;
    return le(l).reduce((c, y) => {
      const b = l[y];
      return (c[y] = d[b]((S) => S[y])), c;
    }, {});
  }
  const h = Fe(s());
  return (
    Pn(() => {
      const d = s();
      (h.touched = d.touched), (h.valid = d.valid), (h.pending = d.pending);
    }),
    M(() =>
      Object.assign(Object.assign({ initialValues: m(t) }, h), {
        valid: h.valid && !le(r.value).length,
        dirty: u.value,
      })
    )
  );
}
function At(e, n, t) {
  const r = An(t),
    l = t == null ? void 0 : t.initialValues,
    u = ae(r),
    s = ae(w(r));
  function h(d, c = !1) {
    (u.value = Me(w(u.value) || {}, w(d))),
      (s.value = Me(w(s.value) || {}, w(d))),
      c &&
        e.value.forEach((y) => {
          if (y.touched) return;
          const S = Y(u.value, y.path);
          ye(n, y.path, w(S));
        });
  }
  return (
    Ae(l) &&
      fe(
        l,
        (d) => {
          d && h(d, !0);
        },
        { deep: !0 }
      ),
    { initialValues: u, originalInitialValues: s, setInitialValues: h }
  );
}
const jt = on({
    name: "Form",
    inheritAttrs: !1,
    props: {
      as: { type: String, default: "form" },
      validationSchema: { type: Object, default: void 0 },
      initialValues: { type: Object, default: void 0 },
      initialErrors: { type: Object, default: void 0 },
      initialTouched: { type: Object, default: void 0 },
      validateOnMount: { type: Boolean, default: !1 },
      onSubmit: { type: Function, default: void 0 },
      onInvalidSubmit: { type: Function, default: void 0 },
      keepValues: { type: Boolean, default: !1 },
    },
    setup(e, n) {
      const t = de(e, "initialValues"),
        r = de(e, "validationSchema"),
        l = de(e, "keepValues"),
        {
          errors: u,
          errorBag: s,
          values: h,
          meta: d,
          isSubmitting: c,
          isValidating: y,
          submitCount: b,
          controlledValues: S,
          validate: g,
          validateField: I,
          handleReset: q,
          resetForm: O,
          handleSubmit: R,
          setErrors: N,
          setFieldError: L,
          setFieldValue: J,
          setValues: X,
          setFieldTouched: k,
          setTouched: ne,
          resetField: T,
        } = St({
          validationSchema: r.value ? r : void 0,
          initialValues: t,
          initialErrors: e.initialErrors,
          initialTouched: e.initialTouched,
          validateOnMount: e.validateOnMount,
          keepValuesOnUnmount: l,
        }),
        U = R((x, { evt: $ }) => {
          mn($) && $.target.submit();
        }, e.onInvalidSubmit),
        z = e.onSubmit ? R(e.onSubmit, e.onInvalidSubmit) : U;
      function te(x) {
        Ke(x) && x.preventDefault(),
          q(),
          typeof n.attrs.onReset == "function" && n.attrs.onReset();
      }
      function ue(x, $) {
        return R(typeof x == "function" && !$ ? x : $, e.onInvalidSubmit)(x);
      }
      function Q() {
        return w(h);
      }
      function G() {
        return w(d.value);
      }
      function K() {
        return w(u.value);
      }
      function P() {
        return {
          meta: d.value,
          errors: u.value,
          errorBag: s.value,
          values: h,
          isSubmitting: c.value,
          isValidating: y.value,
          submitCount: b.value,
          controlledValues: S.value,
          validate: g,
          validateField: I,
          handleSubmit: ue,
          handleReset: q,
          submitForm: U,
          setErrors: N,
          setFieldError: L,
          setFieldValue: J,
          setValues: X,
          setFieldTouched: k,
          setTouched: ne,
          resetForm: O,
          resetField: T,
          getValues: Q,
          getMeta: G,
          getErrors: K,
        };
      }
      return (
        n.expose({
          setFieldError: L,
          setErrors: N,
          setFieldValue: J,
          setValues: X,
          setFieldTouched: k,
          setTouched: ne,
          resetForm: O,
          validate: g,
          validateField: I,
          resetField: T,
          getValues: Q,
          getMeta: G,
          getErrors: K,
        }),
        function () {
          const $ = e.as === "form" ? e.as : sn(e.as),
            re = gn($, n, P);
          if (!e.as) return re;
          const he = e.as === "form" ? { novalidate: !0 } : {};
          return dn(
            $,
            Object.assign(Object.assign(Object.assign({}, he), n.attrs), {
              onSubmit: z,
              onReset: te,
            }),
            re
          );
        }
      );
    },
  }),
  Mt = jt;
export { Ft as F, Mt as a };
