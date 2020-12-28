import jenkins.model.*
import hudson.security.*
import hudson.model.*
import com.cloudbees.plugins.*

File admin_credentials=new File('/var/jenkins-creds/pre-configured-github-credentials.txt')

def instance = Jenkins.getInstance()
def user,pass;
def fileLineCount=1;
def filePointer;
admin_credentials.withReader{
	reader->
	while((filePointer=reader.readLine())!=null){
		if(fileLineCount==3){
			user=filePointer;
		}
		else if(fileLineCount==4){
			pass=filePointer;
		}
		fileLineCount++;
	}
}
def hudsonRealm = new HudsonPrivateSecurityRealm(false)
hudsonRealm.createAccount(user, pass)
instance.setSecurityRealm(hudsonRealm)

def strategy = new GlobalMatrixAuthorizationStrategy()
strategy.add(Jenkins.READ, 'authenticated')
strategy.add(Jenkins.ADMINISTER,user)
strategy.add(Jenkins.READ, user)
strategy.add(credentials.CredentialsProvider.CREATE, user)
strategy.add(credentials.CredentialsProvider.DELETE, user)
strategy.add(credentials.CredentialsProvider.UPDATE, user)
strategy.add(credentials.CredentialsProvider.VIEW, user)
strategy.add(credentials.CredentialsProvider.MANAGE_DOMAINS, user)
strategy.add(Jenkins.MasterComputer.BUILD, user)
strategy.add(Jenkins.MasterComputer.CONFIGURE, user)
strategy.add(Jenkins.MasterComputer.CONNECT, user)
strategy.add(Jenkins.MasterComputer.CREATE, user)
strategy.add(Jenkins.MasterComputer.DELETE, user)
strategy.add(Jenkins.MasterComputer.DISCONNECT, user)
strategy.add(Item.BUILD, user)
strategy.add(Item.CANCEL, user)
strategy.add(Item.CONFIGURE, user)
strategy.add(Item.CREATE, user)
strategy.add(Item.DELETE, user)
strategy.add(Item.DISCOVER, user)
strategy.add(View.READ, user)
strategy.add(View.DELETE, user)
strategy.add(View.CREATE, user)
strategy.add(View.CONFIGURE, user)
instance.setAuthorizationStrategy(strategy)
instance.save()

