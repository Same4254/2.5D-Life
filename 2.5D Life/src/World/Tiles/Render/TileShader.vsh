#version 400 core
#define LIGHT_COUNT 4

in vec2 position;
in vec2 lightInfo;
in vec2 offset;

in vec3 translation;

out float visibility; 
out vec2 textCoord;
out vec3 surfaceNormal;
out vec3 toCameraVector;
out vec3 toLightVector[LIGHT_COUNT];
out float shineDamper;
out float reflectivity;

uniform float numberOfRows;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

uniform vec3 lightPosition[LIGHT_COUNT];

uniform float fogDensity;
uniform float fogGradient;

void main() {
	vec3 pos = vec3(position.x + translation.x, translation.y, position.y + translation.z);

	vec4 worldPosition = vec4(pos.x, pos.y, pos.z, 1);
	vec4 positionRelativeToCam = viewMatrix * worldPosition;
	gl_Position = projectionMatrix * positionRelativeToCam;
	
	float distanceFromCam = length(positionRelativeToCam.xyz);
	visibility = exp(-pow((distanceFromCam*fogDensity), fogGradient));
	visibility = clamp(visibility, 0.0, 1.0);
	
	textCoord = ((position + .5) / numberOfRows) + offset;
	
	surfaceNormal = vec3(0, 1, 0);
	for(int i = 0; i < LIGHT_COUNT; i++) {
		toLightVector[i] = lightPosition[i] - worldPosition.xyz;
	}
	toCameraVector = (inverse(viewMatrix) * vec4(0, 0, 0, 1)).xyz - worldPosition.xyz;
	
	shineDamper = lightInfo.x;
	reflectivity = lightInfo.y;
}