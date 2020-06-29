package com.stock.eason.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;

import org.reactivestreams.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.annotation.ObservableExecutionMode;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import com.stock.eason.bean.User;
import com.stock.eason.util.DBUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class UserServiceImpl implements UserService{

	@Override
	public String register(User user) {
		DBUtil.saveOrUpdate(user);
		return null;
	}

    @HystrixCommand(fallbackMethod = "loginsFallback",
            commandKey = "queryContents",
            groupKey = "querygroup-one",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.semaphore.maxConcurrentRequests",value = "100"),
                    @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000000000")
            },
            threadPoolKey = "queryContentshystrixJackpool", threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "100")
    })
	@Override
	public boolean login(User user) {
		String sql = "select * from User where username = ?";
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(user.getUsername());
		ArrayList<User> list = DBUtil.selectByParam(sql,arrayList ,User.class);
		if(list!=null) {
			User u = (User)list.get(0);
			return u.getPassword().equals(user.getPassword());
		}else {
			return false;
		}
	}
    
    public boolean loginsFallback(User user) {
//    	log.info("===============loginsFallback=================logins fail");
    	return false;
    }

	@Override
	public User findById(Integer id) {
		User us = (User) DBUtil.selectById(id, User.class);
		return us;
	}

	@Override
	public String deleteUser(Integer id) {
		DBUtil.deleteById(id, User.class);
		return null;
	}
	


    private AtomicInteger s = new AtomicInteger();
    private AtomicInteger f = new AtomicInteger();

    public static String SERVIER_NAME = "api-gateway/order";

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Command灞炴��
     * execution.isolation.strategy  鎵ц鐨勯殧绂荤瓥鐣�
     * THREAD 绾跨▼姹犻殧绂荤瓥鐣�  鐙珛绾跨▼鎺ユ敹璇锋眰
     * SEMAPHORE 淇″彿閲忛殧绂荤瓥鐣� 鍦ㄨ皟鐢ㄧ嚎绋嬩笂鎵ц
     * <p>
     * execution.isolation.thread.timeoutInMilliseconds  璁剧疆HystrixCommand鎵ц鐨勮秴鏃舵椂闂达紝鍗曚綅姣
     * execution.timeout.enabled  鏄惁鍚姩瓒呮椂鏃堕棿锛宼rue锛宖alse
     * execution.isolation.semaphore.maxConcurrentRequests  闅旂绛栫暐涓轰俊鍙烽噺鐨勬椂鍊欙紝璇ュ睘鎬ф潵閰嶇疆淇″彿閲忕殑澶у皬锛屾渶澶у苟鍙戣揪鍒颁俊鍙烽噺鏃讹紝鍚庣画璇锋眰琚嫆缁�
     * <p>
     * circuitBreaker.enabled   鏄惁寮�鍚柇璺櫒鍔熻兘
     * circuitBreaker.requestVolumeThreshold  璇ュ睘鎬ц缃湪婊氬姩鏃堕棿绐楀彛涓紝鏂矾鍣ㄧ殑鏈�灏忚姹傛暟銆傞粯璁�20锛屽鏋滃湪绐楀彛鏃堕棿鍐呰姹傛鏁�19锛屽嵆浣�19涓叏閮ㄥけ璐ワ紝鏂矾鍣ㄤ篃涓嶄細鎵撳紑
     * circuitBreaker.sleepWindowInMilliseconds    鏀瑰睘鎬х敤鏉ヨ缃綋鏂矾鍣ㄦ墦寮�涔嬪悗鐨勪紤鐪犳椂闂达紝浼戠湢鏃堕棿缁撴潫鍚庢柇璺櫒涓哄崐寮�鐘舵�侊紝鏂矾鍣ㄨ兘鎺ュ彈璇锋眰锛屽鏋滆姹傚け璐ュ張閲嶆柊鍥炲埌鎵撳紑鐘舵�侊紝濡傛灉璇锋眰鎴愬姛鍙堝洖鍒板叧闂姸鎬�
     * circuitBreaker.errorThresholdPercentage  璇ュ睘鎬ц缃柇璺櫒鎵撳紑鐨勯敊璇櫨鍒嗘瘮銆傚湪婊氬姩鏃堕棿鍐咃紝鍦ㄨ姹傛暟閲忚秴杩嘽ircuitBreaker.requestVolumeThreshold,濡傛灉閿欒璇锋眰鏁扮殑鐧惧垎姣旇秴杩囪繖涓瘮渚嬶紝鏂矾鍣ㄥ氨涓烘墦寮�鐘舵��
     * circuitBreaker.forceOpen   true琛ㄧず寮哄埗鎵撳紑鏂矾鍣紝鎷掔粷鎵�鏈夎姹�
     * circuitBreaker.forceClosed  true琛ㄧず寮哄埗杩涘叆鍏抽棴鐘舵�侊紝鎺ユ敹鎵�鏈夎姹�
     * <p>
     * metrics.rollingStats.timeInMilliseconds   璁剧疆婊氬姩鏃堕棿绐楃殑闀垮害锛屽崟浣嶆绉掋�傝繖涓椂闂寸獥鍙ｅ氨鏄柇璺櫒鏀堕泦淇℃伅鐨勬寔缁椂闂淬�傛柇璺櫒鍦ㄦ敹闆嗘寚鏍囦俊鎭殑鏃朵細鏍规嵁杩欎釜鏃堕棿绐楀彛鎶婅繖涓獥鍙ｆ媶鍒嗘垚澶氫釜妗讹紝姣忎釜妗朵唬琛ㄤ竴娈垫椂闂寸殑鎸囨爣锛岄粯璁�10000
     * metrics.rollingStats.numBuckets   婊氬姩鏃堕棿绐楃粺璁℃寚鏍囦俊鎭垝鍒嗙殑妗剁殑鏁伴噺锛屼絾鏄粴鍔ㄦ椂闂村繀椤昏兘澶熸暣闄よ繖涓《鐨勪釜鏁帮紝瑕佷笉鐒舵姏寮傚父
     * <p>
     * requestCache.enabled   鏄惁寮�鍚姹傜紦瀛橈紝榛樿涓簍rue
     * requestLog.enabled 鏄惁鎵撳嵃鏃ュ織鍒癏ystrixRequestLog涓紝榛樿true
     *
     * @HystrixCollapser 璇锋眰鍚堝苟
     * maxRequestsInBatch  璁剧疆涓�娆¤姹傚悎骞舵壒澶勭悊涓厑璁哥殑鏈�澶ц姹傛暟
     * timerDelayInMilliseconds  璁剧疆鎵瑰鐞嗚繃绋嬩腑姣忎釜鍛戒护寤惰繜鏃堕棿
     * requestCache.enabled   鎵瑰鐞嗚繃绋嬩腑鏄惁寮�鍚姹傜紦瀛橈紝榛樿true
     * <p>
     * threadPoolProperties
     * threadPoolProperties 灞炴��
     * coreSize   鎵ц鍛戒护绾跨▼姹犵殑鏈�澶х嚎绋嬫暟锛屼篃灏辨槸鍛戒护鎵ц鐨勬渶澶у苟鍙戞暟锛岄粯璁�10
     */
//    @HystrixCommand(fallbackMethod = "queryContentsFallback",
//            commandKey = "queryContents",
//            groupKey = "querygroup-one",
//            commandProperties = {
//                    @HystrixProperty(name = "execution.isolation.semaphore.maxConcurrentRequests",value = "100"),
//                    @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
//                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000000000")
//            },
//            threadPoolKey = "queryContentshystrixJackpool", threadPoolProperties = {
//            @HystrixProperty(name = "coreSize", value = "100")
//    })
//    @Override
//    public List<ConsultContent> queryContents() {
//        log.info(Thread.currentThread().getName() + "========queryContents=========");
//        s.incrementAndGet();
//        List<ConsultContent> results = restTemplate.getForObject("http://"
//                + SERVIER_NAME + "/user/queryContent", List.class);
//        return results;
//    }
//
//    @Override
//    public List<ConsultContent> queryContents(HttpServletRequest request) {
//        log.info(Thread.currentThread().getName() + "========queryContents=========");
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", request.getHeader("Authorization"));
//        List<ConsultContent> results = restTemplate.exchange("http://"
//                        + SERVIER_NAME + "/user/queryContent", HttpMethod.GET,
//                new HttpEntity<String>(headers), List.class).getBody();
//        return results;
//    }
//
//    @HystrixCommand(fallbackMethod = "queryContentsAsynFallback")
//    @Override
//    public Future<String> queryContentsAsyn() {
//        return new AsyncResult<String>() {
//            @Override
//            public String invoke() {
//                log.info("========queryContents=========");
//                List<ConsultContent> results = restTemplate.getForObject("http://"
//                        + SERVIER_NAME + "/user/queryContent", List.class);
//                return JSONObject.toJSONString(results);
//            }
//        };
//    }
//
//    public String queryContentsAsynFallback() {
//        log.info("========queryContentsAsynFallback=========");
//        return "queryContentsAsynFallback";
//    }
//
//    @HystrixCommand(threadPoolKey = "queryContentshystrixJackpool")
//    //    @Retryable
//    @Override
//    public List<ConsultContent> queryContent() {
//        log.info("========queryContent=========");
//        List<ConsultContent> results = restTemplate.getForObject("http://"
//                + SERVIER_NAME + "/user/queryContent", List.class);
//        return results;
//    }
//
//    public List<ConsultContent> queryContentsFallback() {
//        f.incrementAndGet();
//        log.info("===============queryContentsFallback=================");
//
//        return null;
//    }
//
//    @Override
//    public String queryMonitor() {
//        JSONObject jo = new JSONObject();
//        jo.put("鎴愬姛鏁�", s.get());
//        jo.put("澶辫触鏁�", f.get());
//        return jo.toJSONString();
//    }
//
//    /*
//     *  ObservableExecutionMode.EAGER  琛ㄧず浣跨敤observe()鏂瑰紡鎵ц锛屾槸hot Observeable
//     *  ObservableExecutionMode.LAZY   琛ㄧず浣跨敤toObservable()鎵ц锛屾槸cold Observeable
//     */
//    @HystrixCommand(fallbackMethod = "exceptionHandler",
//            observableExecutionMode = ObservableExecutionMode.LAZY,
//            commandProperties = {@HystrixProperty(name = "execution.isolation.strategy", value = "THREAD")},
//            threadPoolKey = "hystrixJackpool", threadPoolProperties = {@HystrixProperty(name = "coreSize", value = "10")})
//    @Override
//    public Observable<String> mergeResult() {
//        log.info("===================" + Thread.currentThread().getName()
//                + "================");
//
//        return Observable.create(new Observable.OnSubscribe<String>() {
//
//            public void call(Subscriber<? super String> observer) {
//                log.info("==================="
//                        + Thread.currentThread().getName() + "================");
//                try {
//                    if (!observer.isUnsubscribed()) {
//                        log.info(Thread.currentThread().getName()
//                                + "===============onNext  invoke=================");
//                        List<ConsultContent> results = restTemplate.getForObject("http://"
//                                + SERVIER_NAME + "/user/queryContent", List.class);
//                        log.info(JSONObject.toJSONString(results));
//                        observer.onNext(JSONObject.toJSONString(results));
//
//                        List<ZgGoods> goods = restTemplate.getForObject("http://"
//                                + SERVIER_NAME + "/goods/queryGoods", List.class);
//                        log.info(JSONObject.toJSONString(goods));
//                        observer.onNext(JSONObject.toJSONString(goods));
//                        observer.onCompleted();
//                    }
//                } catch (Exception e) {
//                    observer.onError(e);
//                }
//            }
//        });
//    }
//
//    public Observable<String> exceptionHandler() {
//        return Observable.just("鎴戦敊浜嗭紒锛侊紒");
//    }


}
