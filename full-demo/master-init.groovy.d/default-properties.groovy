import jenkins.model.*
import hudson.security.*
import hudson.security.csrf.DefaultCrumbIssuer

def instance = Jenkins.getInstance()

def env = System.getenv()
def username
def passwd
new File(env.JENKINS_ADMIN_USER_FILE).withReader { username = it.readLine() }
new File(env.JENKINS_ADMIN_PASSWORD_FILE).withReader { passwd = it.readLine() }

def realm = new HudsonPrivateSecurityRealm(false)
realm.createAccount(username, passwd)
instance.setSecurityRealm(realm)

def strategy = new GlobalMatrixAuthorizationStrategy()
strategy.add(Jenkins.ADMINISTER, username)
instance.setAuthorizationStrategy(strategy)

instance.getDescriptor("jenkins.CLI").get().setEnabled(false)
instance.setCrumbIssuer(new DefaultCrumbIssuer(true))
instance.setNumExecutors(0)
instance.save()