package edu.iis.mto.staticmock;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(PowerMockRunner.class)
@PrepareForTest({NewsReaderFactory.class, ConfigurationLoader.class})
public class NewsLoaderTest {

    private IncomingInfo testCOntent1;
    private IncomingInfo testCOntent2;
    private IncomingInfo testCOntent3;
    private IncomingInfo testCOntent4;
    private IncomingNews incomingNews = new IncomingNews();
    private NewsLoader newsLoader = new NewsLoader();

    @Before
    public void setup(){

        testCOntent1 = new IncomingInfo("testCOntent", SubsciptionType.A);
        incomingNews.add(testCOntent1);
        testCOntent2 = new IncomingInfo("testCOntent", SubsciptionType.B);
        incomingNews.add(testCOntent2);
        testCOntent3 = new IncomingInfo("testCOntent", SubsciptionType.C);
        incomingNews.add(testCOntent3);
        testCOntent4 = new IncomingInfo("testCOntent", SubsciptionType.NONE);
        incomingNews.add(testCOntent4);
    }

    @Test
    public void sholudProperlySplitIncomingNews() throws Exception {
        PublishableNews publishableNewsActual = Whitebox.invokeMethod(newsLoader, "prepareForPublish", incomingNews);

        PublishableNews publishableNewsExpected = new PublishableNews();
        publishableNewsExpected.addForSubscription(testCOntent1.getContent(),testCOntent1.getSubscriptionType());
        publishableNewsExpected.addForSubscription(testCOntent2.getContent(),testCOntent2.getSubscriptionType());
        publishableNewsExpected.addForSubscription(testCOntent3.getContent(),testCOntent3.getSubscriptionType());

        publishableNewsExpected.addPublicInfo(testCOntent4.getContent());

        assertThat(publishableNewsActual,is(publishableNewsExpected));
    }

    @Test
    public void shouldCallOncePubliherMetod() throws Exception {

        NewsLoader spy = PowerMockito.spy(new NewsLoader());
        PowerMockito.verifyPrivate(spy, Mockito.times(1)).invoke("prepareForPublish",incomingNews);

    }

}
