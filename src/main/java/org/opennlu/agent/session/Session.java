package org.opennlu.agent.session;

import org.opennlu.agent.Agent;
import org.opennlu.agent.AgentResponse;
import org.opennlu.agent.context.Context;
import org.opennlu.agent.intent.Parameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by René Preuß on 8/31/2017.
 */
public class Session {
    private final SessionManager sessionManager;
    private final Agent agent;

    private List<Context> inputContext = new ArrayList<>();
    private Map<String, String> inputParameters = new HashMap<>();

    public Session(SessionManager sessionManager, Agent agent) {
        this.sessionManager = sessionManager;
        this.agent = agent;
    }

    public AgentResponse parse(String message) throws Exception {
        AgentResponse agentResponse = agent.getTrainingManager().parse(message, inputContext, inputParameters);
        this.inputContext = agentResponse.getContext();
        this.inputParameters = new HashMap<>();
        for(Parameter parameter : agentResponse.getIntent().getParameters()) {
            if(agentResponse.getEntityValues().containsKey(parameter.getEntity().getName()))
                this.inputParameters.put(parameter.getName(), agentResponse.getEntityValues().get(parameter.getEntity().getName()));
        }
        return agentResponse;
    }

    public List<Context> getInputContext() {
        return inputContext;
    }

    public Map<String, String> getInputParameters() {
        return inputParameters;
    }
}