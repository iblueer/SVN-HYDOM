<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${product.name} ${message("shop.consultation.title")}</title>


<link href="${base}/resources/shop/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/shop/css/product.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/shop/css/consultation.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/header_view.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $addConsultation = $("#addConsultation");
	
	[#if setting.consultationAuthority != "anyone"]
		$addConsultation.click(function() {
			if ($.checkLogin()) {
				return true;
			} else {
				$.redirectLogin("${base}/consultation/add/${product.id}.jhtml", "${message("shop.consultation.accessDenied")}");
				return false;
			}
		});
	[/#if]
	
});
</script>
</head>
<body>
	[#include "/shop/include/header.ftl" /]
	<div class="container consultation">
		<div class="span6">
			<div class="hotProductCategory">
				[@product_category_root_list]
					[#list productCategories as category]
						<dl[#if !category_has_next] class="last"[/#if]>
							<dt>
								<a href="${base}${category.path}">${category.name}</a>
							</dt>
							[@product_category_children_list productCategoryId = category.id count = 4]
								[#list productCategories as productCategory]
									<dd>
										<a href="${base}${productCategory.path}">${productCategory.name}</a>
									</dd>
								[/#list]
							[/@product_category_children_list]
						</dl>
					[/#list]
				[/@product_category_root_list]
			</div>
			<div class="hotProduct">
				<div class="title">${message("shop.product.hotProduct")}</div>
				<ul>
					[@product_list count = 6 orderBy="monthSales desc"]
						[#list products as product]
							<li[#if !product_has_next] class="last"[/#if]>
								<a href="${base}${product.path}" title="${product.name}">${abbreviate(product.name, 30)}</a>
								[#if product.scoreCount > 0]
									<div>
										<div>${message("Product.score")}: </div>
										<div class="score${(product.score * 2)?string("0")}"></div>
										<div>${product.score?string("0.0")}</div>
									</div>
								[/#if]
								<div>${message("Product.price")}: <strong>${currency(product.price, true, true)}</strong></div>
								<div>${message("Product.monthSales")}: <em>${product.monthSales}</em></div>
							</li>
						[/#list]
					[/@product_list]
				</ul>
			</div>
		</div>
		<div class="span18 last">
			<div class="path">
				<ul>
					<li>
						<a href="${base}/">${message("shop.path.home")}</a>
					</li>
					<li>
						<a href="${base}${product.path}">${abbreviate(product.name, 30)}</a>
					</li>
					<li>${message("shop.consultation.title")}</li>
				</ul>
			</div>
			<table class="info">
				<tr>
					<th rowspan="3">
						<img src="[#if product.thumbnail??]${product.thumbnail}[#else]${setting.defaultThumbnailProductImage}[/#if]" alt="${product.name}" />
					</th>
					<td>
						<a href="${base}${product.path}">${abbreviate(product.name, 60, "...")}</a>
					</td>
					<td class="handle" rowspan="3">
						<a href="${base}/consultation/add/${product.id}.jhtml" id="addConsultation">[${message("shop.consultation.add")}]</a>
					</td>
				</tr>
				<tr>
					<td>
						${message("Product.price")}: <strong>${currency(product.price, true, true)}</strong>
					</td>
				</tr>
				<tr>
					<td>
						[#if product.scoreCount > 0]
							<div>${message("Product.score")}: </div>
							<div class="score${(product.score * 2)?string("0")}"></div>
							<div>${product.score?string("0.0")}</div>
						[#else]
							[#if setting.isShowMarketPrice]
								${message("Product.marketPrice")}:
								<del>${currency(product.marketPrice, true, true)}</del>
							[/#if]
						[/#if]
					</td>
				</tr>
			</table>
			<div class="content">
				[#if page.content?has_content]
					<ul>
						[#list page.content as consultation]
							<li[#if !consultation_has_next] class="last"[/#if]>
								${consultation.content}
								<span>
									[#if consultation.member??]
										${consultation.member.username}
									[#else]
										${message("shop.consultation.anonymous")}
									[/#if]
									<span title="${consultation.createDate?string("yyyy-MM-dd HH:mm:ss")}">${consultation.createDate}</span>
								</span>
								[#if consultation.replyConsultations?has_content]
									<div class="arrow"></div>
									<ul>
										[#list consultation.replyConsultations as replyConsultation]
											<li>
												${replyConsultation.content}
												<span title="${replyConsultation.createDate?string("yyyy-MM-dd HH:mm:ss")}">${replyConsultation.createDate}</span>
											</li>
										[/#list]
									</ul>
								[/#if]
							</li>
						[/#list]
					</ul>
				[#else]
					<p>${message("shop.consultation.noResult")}</p>
				[/#if]
			</div>
			[@pagination pageNumber = page.pageNumber totalPages = page.totalPages pattern = "?pageNumber={pageNumber}"]
				[#include "/shop/include/pagination.ftl"]
			[/@pagination]
		</div>
	</div>
	[#include "/shop/include/footer.ftl" /]
</body>
</html>