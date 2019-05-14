package client;

import org.apache.juddi.api_v3.AccessPointType;
import org.uddi.api_v3.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.List;
import java.util.stream.Collectors;


public class WebServiceClient {

    private static BufferedReader in;
    private static JuddiClient juddiClient;

    public static void main(String[] args) throws Exception {
        in = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter JUDDI username");
        String username = in.readLine().trim();
        System.out.println("Enter JUDDI user password");
        String password = in.readLine().trim();

        juddiClient = new JuddiClient("META-INF/uddi.xml");
        juddiClient.authenticate(username, password);

		int command = 0;
		while (true) {
			switch (command) {
				case 0:
					System.out.println("Select action:");
					System.out.println("1) show business list");
					System.out.println("2) register business");
					System.out.println("3) register service");
					System.out.println("4) find service");
					System.out.println("5) exit");
					command = Integer.parseInt(in.readLine());
					break;
				case 1:
					listBusinesses(null);
					command=0;
					break;
				case 2:
					System.out.println("enter business name");
					String bnn = readString(in);
					if (bnn != null) {
						createBusiness(bnn);
					}
					command = 0;
					break;
				case 3:
					listBusinesses(null);
					String bbk;
					do {
						System.out.println("enter business key");
						bbk = readString(in);
					} while (bbk == null);

					String ssn;
					do {
						System.out.println("enter service key");
						ssn = readString(in);
					} while (ssn == null);

					String ssurl;
					do {
						System.out.println("enter wsdl link");
						ssurl = readString(in);
					} while (ssurl == null);
					createService(bbk, ssn, ssurl);
					command = 0;
					break;
				case 4:
					System.out.println("enter service name to find");
					String ffsn = readString(in);
					filterServices(ffsn);
					System.out.println("enter service key");
					String kkey = readString(in);
					if (kkey != null) {
						useService(kkey);
					}
					command = 0;
					break;
				case 5:
					return;
				default:
					command = 0;
					break;

			}
		}
    }

    private static void useService(String serviceKey) throws RemoteException {
        ServiceDetail serviceDetail = juddiClient.getService(serviceKey.trim());
        if (serviceDetail == null || serviceDetail.getBusinessService() == null || serviceDetail.getBusinessService().isEmpty()) {
            System.out.printf("Can not find service by key '%s'\b", serviceKey);
            return;
        }
        List<BusinessService> services = serviceDetail.getBusinessService();
        BusinessService businessService = services.get(0);
        BindingTemplates bindingTemplates = businessService.getBindingTemplates();
        if (bindingTemplates == null || bindingTemplates.getBindingTemplate().isEmpty()) {
            System.out.printf("No binding template found for service '%s' '%s'\n", serviceKey, businessService.getBusinessKey());
            return;
        }
        for (BindingTemplate bindingTemplate : bindingTemplates.getBindingTemplate()) {
            AccessPoint accessPoint = bindingTemplate.getAccessPoint();
            if (accessPoint.getUseType().equals(AccessPointType.END_POINT.toString())) {
                String value = accessPoint.getValue();
                System.out.printf("Using endpoint '%s'\n", value);
                return;
            }
        }
        System.out.printf("No endpoint found for service '%s'\n", serviceKey);
    }


    private static void createService(String businessKey, String serviceName, String wsdlUrl) throws Exception {
        List<ServiceDetail> serviceDetails = juddiClient.publishUrl(businessKey.trim(), serviceName.trim(), wsdlUrl.trim());
        System.out.printf("Services published from wsdl %s\n", wsdlUrl);
        JuddiUtil.printServicesInfo(serviceDetails.stream()
                .map(ServiceDetail::getBusinessService)
                .flatMap(List::stream)
                .collect(Collectors.toList())
        );
    }

    private static void createBusiness(String businessName) throws RemoteException {
        businessName = businessName.trim();
        BusinessDetail business = juddiClient.createBusiness(businessName);
        System.out.println("New business was created");
        for (BusinessEntity businessEntity : business.getBusinessEntity()) {
            System.out.printf("Key: '%s'\n", businessEntity.getBusinessKey());
            System.out.printf("Name: '%s'\n", businessEntity.getName().stream().map(Name::getValue).collect(Collectors.joining(" ")));
        }
    }

    private static void filterServices(String filterArg) throws RemoteException {
        List<BusinessService> services = juddiClient.getServices(filterArg);
        JuddiUtil.printServicesInfo(services);
    }

    private static void listBusinesses(Void ignored) throws RemoteException {
        JuddiUtil.printBusinessInfo(juddiClient.getBusinessList().getBusinessInfos());
    }

    private static String readString(BufferedReader reader) throws IOException {
        String trim = reader.readLine().trim();
        if (trim.isEmpty()) {
            return null;
        }
        return trim;
    }
}