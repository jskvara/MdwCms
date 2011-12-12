package cms.util;

import com.google.appengine.api.quota.QuotaService;
import com.google.appengine.api.quota.QuotaServiceFactory;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class LogFilter implements Filter {

	FilterConfig fconf = null;
    public void init(FilterConfig fc) throws ServletException {
        this.fconf = fc;
    }
    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc)
			throws IOException, ServletException {
		QuotaService qs = QuotaServiceFactory.getQuotaService();
		double cpuSeconds;
		double apiSeconds = 0;
		if (qs.supports(QuotaService.DataType.CPU_TIME_IN_MEGACYCLES)) {
			long startAPI = qs.getApiTimeInMegaCycles();
			long startCPU = qs.getCpuTimeInMegaCycles();
			fc.doFilter(sr, sr1);
			long endCPU = qs.getCpuTimeInMegaCycles();
			long endAPI = qs.getApiTimeInMegaCycles();
			cpuSeconds = qs.convertMegacyclesToCpuSeconds(endCPU - startCPU);
			apiSeconds = qs.convertMegacyclesToCpuSeconds(endAPI - startAPI);
		} else {
			long startCPU = System.currentTimeMillis();
			fc.doFilter(sr, sr1);
			long endCPU = System.currentTimeMillis();
			cpuSeconds = (endCPU - startCPU)/1000;
		}
		System.out.println("Scalability: cpu = " + cpuSeconds + ", "
				+ "api = " + apiSeconds + ", "
				+ "sum = " + (cpuSeconds + apiSeconds));
    }
    public void destroy() {
        this.fconf = null;
    }
}
