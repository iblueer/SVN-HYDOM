/*
 * 
 * 
 * 
 */
package net.shop.template.directive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shop.Filter;
import net.shop.Order;
import net.shop.entity.Brand;
import net.shop.entity.Consultation;
import net.shop.entity.Member;
import net.shop.entity.Product;
import net.shop.service.ConsultationService;
import net.shop.service.MemberService;
import net.shop.service.ProductService;
import net.shop.util.FreemarkerUtils;

import org.springframework.stereotype.Component;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 咨询
 * 
 * 
 * 
 */
@Component("consultationListDirective")
public class ConsultationListDirective extends BaseDirective {

	/** "会员ID"参数名称 */
	private static final String MEMBER_ID_PARAMETER_NAME = "memberId";

	/** "商品ID"参数名称 */
	private static final String PRODUCT_ID_PARAMETER_NAME = "productId";

	/** 变量名称 */
	private static final String VARIABLE_NAME = "consultations";

	@Resource(name = "consultationServiceImpl")
	private ConsultationService consultationService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Long memberId = FreemarkerUtils.getParameter(MEMBER_ID_PARAMETER_NAME, Long.class, params);
		Long productId = FreemarkerUtils.getParameter(PRODUCT_ID_PARAMETER_NAME, Long.class, params);

		Member member = memberService.find(memberId);
		Product product = productService.find(productId);

		List<Consultation> consultations;
		boolean useCache = useCache(env, params);
		String cacheRegion = getCacheRegion(env, params);
		Integer count = getCount(params);
		List<Filter> filters = getFilters(params, Brand.class);
		List<Order> orders = getOrders(params);
		if ((memberId != null && member == null) || (productId != null && product == null)) {
			consultations = new ArrayList<Consultation>();
		} else {
			if (useCache) {
				consultations = consultationService.findList(member, product, true, count, filters, orders, cacheRegion);
			} else {
				consultations = consultationService.findList(member, product, true, count, filters, orders);
			}
		}
		setLocalVariable(VARIABLE_NAME, consultations, env, body);
	}

}