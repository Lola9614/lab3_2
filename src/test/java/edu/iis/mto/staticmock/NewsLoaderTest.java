package edu.iis.mto.staticmock;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(PowerMockRunner.class)
@PrepareForTest({NewsReaderFactory.class, ConfigurationLoader.class})
class NewsLoaderTest {

    public NewsLoaderTest() {
    }

    private IncomingInfo testCOntent1;
    private IncomingInfo testCOntent2;
    private IncomingInfo testCOntent3;

    @Test public void setup() throws Exception {
        NewsLoader newsLoader = new NewsLoader();

        IncomingNews incomingNews = new IncomingNews();
        testCOntent1 = new IncomingInfo("testCOntent", SubsciptionType.A);
        incomingNews.add(testCOntent1);
        testCOntent2 = new IncomingInfo("testCOntent", SubsciptionType.B);
        incomingNews.add(testCOntent2);
        testCOntent3 = new IncomingInfo("testCOntent", SubsciptionType.C);
        incomingNews.add(testCOntent3);

        IncomingInfo testCOntent4 = new IncomingInfo("testCOntent", SubsciptionType.NONE);
        incomingNews.add(testCOntent4);

        PublishableNews publishableNewsActual = Whitebox.invokeMethod(newsLoader, "prepareForPublish", incomingNews);

        PublishableNews publishableNewsExpected = new PublishableNews();
        publishableNewsExpected.addForSubscription(testCOntent1.getContent(),testCOntent1.getSubscriptionType());
        publishableNewsExpected.addForSubscription(testCOntent2.getContent(),testCOntent2.getSubscriptionType());
        publishableNewsExpected.addForSubscription(testCOntent3.getContent(),testCOntent3.getSubscriptionType());

        publishableNewsExpected.addPublicInfo(testCOntent4.getContent());

        assertThat(publishableNewsActual,is(publishableNewsExpected));
    }

}
