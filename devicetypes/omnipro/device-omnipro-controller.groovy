/**
 *  OmniPro Controller
 *
 *  Copyright 2016 Ryan Wagoner
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
metadata {
	definition (name: "OmniPro Controller", namespace: "excaliburpartners", author: "Ryan Wagoner") {
		capability "Refresh"
	}

	simulator {
		// TODO: define status and reply messages here
	}

	tiles {
		standardTile("refresh", "device.switch", inactiveLabel: false, decoration: "flat") {
			state "default", label:'refresh', action:"refresh.refresh", icon:"st.secondary.refresh"
		}
		
		main(["refresh"])
		details(["refresh"])
	}
}

// parse events into attributes
def parse(String description) {
	log.debug "Parsing '${description}'"
	def msg = parseLanMessage(description)
	def type = msg.headers['type'].toUpperCase();	
	def data = msg.json
	log.debug "Parsed ${type} - ${data}"
	
	parent.passToChild(type, data);
}

def refresh() {
	log.debug "Executing 'refresh'"
	parent.subscribeController()
	parent.setupDevices()
}