import com.cloudbees.plugins.credentials.impl.*;
import com.cloudbees.plugins.credentials.*;
import com.cloudbees.plugins.credentials.domains.*;
File _credentials=new File('/var/jenkins-creds/pre-configured-github-credentials.txt')
def username,pass,fileLinePointer;
def fileLineCount=1;
def credentialId="github-creds", description="Github credentials";
_credentials.withReader{
	reader->
	while((fileLinePointer=reader.readLine())!=null){
		if(fileLineCount==1){
			username=fileLinePointer;
		}
		else if(fileLineCount==2){
			pass=fileLinePointer;
		}
		fileLineCount++;
	}
}
Credentials cred = (Credentials) new UsernamePasswordCredentialsImpl(CredentialsScope.GLOBAL,credentialId, description,username, pass)

SystemCredentialsProvider.getInstance().getStore().addCredentials(Domain.global(), cred)
