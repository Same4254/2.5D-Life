#version 400 core
#define LIGHT_COUNT 4

in vec2 textCoord;
in vec3 surfaceNormal;

in vec3 toLightVector[LIGHT_COUNT];
in vec3 toCameraVector;
in float visibility;
in float shineDamper;
in float reflectivity;

out vec4 color_out;

uniform sampler2D texture0;

uniform vec3 lightColor[LIGHT_COUNT];
uniform vec3 lightAttenuation[LIGHT_COUNT];
uniform vec3 skyColor;

void main(void) {
	vec3 unitNormal = normalize(surfaceNormal);
	vec3 unitVectorToCamera = normalize(toCameraVector);
	
	vec3 totalDiffuse = vec3(0);
	vec3 totalSpecular = vec3(0);
	
	for(int i = 0; i < LIGHT_COUNT; i ++) {
		float distanceToLight = length(toLightVector[i]);
		float attFactor = lightAttenuation[i].x + (lightAttenuation[i].y * distanceToLight) + (lightAttenuation[i].z * distanceToLight * distanceToLight);
		
		vec3 unitLightVector = normalize(toLightVector[i]);		
		float nDotl = dot(unitNormal, unitLightVector);
		float brightness = max(nDotl, 0.0);
		
		vec3 lightDirection = -unitLightVector;
		vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
		
		float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
		specularFactor = max(specularFactor, 0.0);
		float dampedFactor = pow(specularFactor, shineDamper);
		
		totalDiffuse = totalDiffuse + (brightness * lightColor[i]) / attFactor;
		totalSpecular = totalSpecular + (dampedFactor * reflectivity * lightColor[i]) / attFactor;
	}
	
	totalDiffuse = max(totalDiffuse, 0.1);
	
	color_out = vec4(totalDiffuse, 1) * texture(texture0, textCoord) + vec4(totalSpecular, 1);// * texture(texture0, texCoordPass)
	color_out = mix(vec4(skyColor, 1.0), color_out, visibility);
	
	//vec4 dummy = color_out + 2;
	//color_out = mix(vec4(1, 0, 0, 1), color_out, .5);
	//color_out = vec4(1, 0, 0, 1);
}