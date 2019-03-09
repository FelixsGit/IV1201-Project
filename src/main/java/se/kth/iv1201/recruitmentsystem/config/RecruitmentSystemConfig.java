package se.kth.iv1201.recruitmentsystem.config;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.Locale;

@EnableTransactionManagement
@EnableWebMvc
@Configuration
public class RecruitmentSystemConfig implements WebMvcConfigurer, ApplicationContextAware {
    private ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setCharacterEncoding("UTF-8");
        viewResolver.setContentType("text/html; charset=UTF-8");
        viewResolver.setTemplateEngine(templateEngine());
        return viewResolver;
    }

    @Bean(name = "recruitmentSystemTemplateEngine")
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        templateEngine.addDialect(new SpringSecurityDialect());
        templateEngine.addDialect(new LayoutDialect());
        return templateEngine;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(this.applicationContext);
        templateResolver.setPrefix("classpath:/web-root/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(true);
        return templateResolver;
    }

    /**
     * Configuration of requests for static files.
     **/
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        int cachePeriodForStaticFilesInSecs = 1;
        String rootDirForStaticFiles = "classpath:/web-root/";

        registry.addResourceHandler("/**")
                .addResourceLocations(rootDirForStaticFiles)
                .setCachePeriod(cachePeriodForStaticFilesInSecs)
                .resourceChain(true).addResolver(new PathResourceResolver());
    }

    /**
     * Register the i18n interceptor.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    /**
     * Create a <code>org.springframework.web.servlet.i18n
     * .LocaleChangeInterceptor</code> for locale management.
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        String nameOfHttpParamForLangCode = "lang";
        String[] allowedHttpMethodsForLocaleChange = {"GET", "POST"};

        LocaleChangeInterceptor i18nBean = new LocaleChangeInterceptor();
        i18nBean.setParamName(nameOfHttpParamForLangCode);
        i18nBean.setHttpMethods(allowedHttpMethodsForLocaleChange);
        i18nBean.setIgnoreInvalidLocale(true);
        return i18nBean;
    }

    /**
     * Create a <code>org.springframework.web.servlet.i18n
     * .SessionLocaleResolver</code> that stores the user's current locale in
     * the session object.
     */
    @Bean
    public CookieLocaleResolver localeResolver()
    {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(new Locale("en"));
        return new CookieLocaleResolver();
    }

    /**
     * Create a <code>org.springframework.context.support.
     * ReloadableResourceBundleMessageSource</code> that loads resource
     * bundles for i18n.
     */
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        String l10nMsgDir = "classpath:/i18n/Messages";
        String l10nValidationMsgDir = "classpath:/i18n/ValidationMessages";
        ReloadableResourceBundleMessageSource resource =
                new ReloadableResourceBundleMessageSource();
        resource.addBasenames(l10nMsgDir, l10nValidationMsgDir);
        resource.setDefaultEncoding("ISO-8859-1");
        resource.setFallbackToSystemLocale(false);
        return resource;
    }

    /**
     * Use the bean defined by {@link #messageSource()} for validation messages.
     */
    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource());
        return validator;
    }
}
