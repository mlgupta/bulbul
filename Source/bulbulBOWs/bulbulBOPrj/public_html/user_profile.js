function setRights(thisForm){
  var objModules=thisForm.hdnModules;
  var objPermissionValues=thisForm.hdnPermissionValues;
  if(typeof objModules !='undefined'){
    if (typeof objModules.length !='undefined'){
      for(var moduleCount=0;  moduleCount<objModules.length; moduleCount++){
        var moduleId  = objModules[moduleCount].value;
        objPermissionValues[moduleCount].value=getPermissions(moduleId,thisForm);
      }
    }else{
      var moduleId = objModules.value;
      objPermissionValues.value=getPermissions(moduleId);
    }
  }else{
    alert('User Access Rights are Not Implemented - Contact Administrator');
    return false;
  }
}
      

function getPermissions(moduleId,thisForm){
  var objPermissions = eval('thisForm.permission'+ moduleId);  
  if(typeof objPermissions !='undefined'){
    if(typeof objPermissions.length !='undefined'){
      var permissionString="";  
      for(var permissionCount=0; permissionCount<objPermissions.length; permissionCount++){
        permissionString=permissionString+((objPermissions[permissionCount].checked)?'1':'0')+'|';
      } 
    }else{
      permissionString=((objPermissions.checked)?'1':'0')+'|';
    }
    return permissionString;
  }else{
    alert('User Access Permissions are Not Implemented - Contact Administrator');
    return false;
  }
}


function getRights(thisForm){
  var objModules=thisForm.hdnModules;
  var objPermissionValues=thisForm.hdnPermissionValues;
  if(typeof objModules !='undefined'){
    if (typeof objModules.length !='undefined'){
      for(var moduleCount=0;  moduleCount<objModules.length; moduleCount++){
        var moduleId  = objModules[moduleCount].value;
        setPermissions(moduleId,thisForm,objPermissionValues[moduleCount].value);
      }
    }else{
      var moduleId = objModules.value;
      setPermissions(moduleId,thisForm,objPermissionValues.value);
    }
  }else{
    alert('User Access Rights are Not Implemented - Contact Administrator');
    return false;
  }
}


function setPermissions(moduleId,thisForm,permissions){
  //permissions="1|1|0|0|1|"
  var objPermissiones = eval('thisForm.permission'+ moduleId);  
  var delimiterIndex=0;
  if(typeof objPermissiones !='undefined'){
    if(typeof objPermissiones.length !='undefined'){
      var permissionString="";  
      for(var permissionCount=0; permissionCount<objPermissiones.length; permissionCount++){
        delimiterIndex=permissions.indexOf('|');
        permissionString=permissions.substring((delimiterIndex-1),1);
        permissions=permissions.substring(delimiterIndex+1)
        objPermissiones[permissionCount].checked=((permissionString==1)?true:false);
      } 
    }else{
      delimiterIndex=permissions.indexOf('|');
      permissionString=permissions.substring((delimiterIndex-1),1);
      objPermissiones.checked=((permissionString==1)?true:false);
    }
    return permissionString;
  }else{
    alert('User Access Permissions are Not Implemented - Contact Administrator');
    return false;
  }
}
      
function permisionCheck(objCurrentPermission, thisForm){
  var objPermissionId = objCurrentPermission.id;
  var objPermission = eval('thisForm.'+objPermissionId);
  if(typeof objPermission != 'undefined'){
    if (typeof objPermission.length != 'undefined'){
      if (objCurrentPermission.checked){
        objPermission[0].checked=true;
      }
      if(!(objPermission[0].checked)){
        for(var intCount=1; intCount<objPermission.length; intCount++){
          objPermission[intCount].checked=false;
        }
      }
            
    }
  }
}
