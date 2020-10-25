# smurf
The name comes from the film "The Smurfs"

SMURF=Smart Micro-services' Ultra Remarkable Framework

## 亮点1-智能路由
智能路由区别于我们通常讲的灰度路由、金丝雀发布、A/B Test、蓝绿发布，智能路由可以包含以上所有这些概念，
并且首家提出采用表达式语言的方式来实现路由选择。

智能路由的规则可动态配置，配置后即时生效，按规则分发流量到下一跳，规则形如：

如果客户端满足：#r['mobile'] matches '158211[0-9]{5}' 

则筛选服务端实例：#s['area'] == 'shanghai'


如果客户端满足：#r['mobile'] not matches '158211[0-9]{5}' 

则筛选服务端实例：#s['area'] == 'beijing'


如果客户端满足：true 

则筛选服务端实例：true

最后一条被认为是兜底规则，即如果上面两条规则都不满足时，可随意选择实例。


## 亮点2-脚手架/骨架
Smurf的脚手架可以在不修改任何一行代码，而是修改模板文件和配置即能完成升级，还能类似平台一样提供扩展点供外部扩展，同样不需要修改代码。

## 亮点3-模型校验器
对标Spring的validation和hibernate的validator，不同于以上两款，Smurf的Checker可以实现如下功能：
- 有条件校验，区别于Spring的group属性是静态代码，checker可以做到动态修改（想象一下如果一个供应商提一个奇葩需求，需要立马实现怎么办？）
- 模型可继承，但校验规则可不一样，同款这些校验都是写在属性上，代码写完即意味着校验规则已定，但Checker因为校验规则和业务代码分离，可按场景区别校验
- 因为校验规则和业务代码分离，代码整洁干净，却又可以动态修改即时生效

以上无任哪一点都比Spring和hibernate的同款优秀！

## 限流-有亮点但未展现

### Guava实现

### Sentinel实现

