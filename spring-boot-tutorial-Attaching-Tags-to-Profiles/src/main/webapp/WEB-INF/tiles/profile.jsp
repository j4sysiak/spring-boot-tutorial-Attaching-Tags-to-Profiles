<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<c:url var="profilePhoto" value="/profilephoto/${userId}" />
<c:url var="editProfileAbout" value="/edit-profile-about" />
<c:url var="saveInterest" value="/save-interest" />
<c:url var="deleteInterest" value="/delete-interest" />

<div class="row">

	<div class="col-md-10 col-md-offset-1">
	
		<div id="profile-photo-status">Photo uploaded</div>
		
		<div id="interestDiv">
			<ul id="interestList">
				<li>Add your interests here (example: music)!</li>  
			</ul>
		</div>

		<div class="profile-about">

			<div class="profile-image">
				<div>
					<img id= "profilePhotoImage" src="${profilePhoto}" />
				</div>
				
				<div class="text-center">
					<a href="#" id="uploadLink">Upload photo</a>
				</div>
			</div>

			<div class="profile-text">
				<c:choose>
					<c:when test="${profile.about == null}">
						Click 'edit' to add information about yourself to your profile
					</c:when>
						<c:otherwise>
							${profile.about}
						</c:otherwise>
				</c:choose>
			</div>

		</div>

		<div class="profile-about-edit">
			<a href="${editProfileAbout}">edit</a>
		</div>
		 
		
		<c:url value="/upload-profile-photo" var="uploadPhotoLink" />
		<form method="post" enctype="multipart/form-data" id="photoUploadForm" action="${uploadPhotoLink}" >
			
			<input type="file" accept="image/*" name="file" id="photoFileInput" />
			<input type="submit" value="upload" />
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		
		</form>

	</div>
</div>



<script>

function setUploadStatusText(text) {
	$("#profile-photo-status").text(text);
	
	window.setTimeout(function() {
		$("#profile-photo-status").text("");
	}, 2000);
}

function uploadSuccess(data) {
	
	$("#profilePhotoImage").attr("src", "${profilePhoto}?time=" + new Date().getMilliseconds());
	
 	$("#photoFileInput").val("");
	
 	setUploadStatusText(data.message);
}

function uploadPhoto(event) {
	
	$.ajax({
		url: $(this).attr("action"),
		type: 'POST',
		data: new FormData(this),
		processData: false,
		contentType: false,
		success: uploadSuccess,
		error: function() {
			setUploadStatusText("Error: Image upload failed (invalid image?)");
		}
	});
	
	event.preventDefault();
}

function saveInterest(text) {
	 editInterest(text, "${saveInterest}");
}

function deleteInterest(text) {
	 editInterest(text, "${deleteInterest}");
}

function editInterest(text, actionUrl) {
	alert(text +": "+ actionUrl);
}




$(document).ready(function() {
	
// 	setUploadStatusText("Hello there.");
// 	console.log("Document loaded.");

	$("#interestList").tagit({
		
		afterTagRemoved : function(event, ui) {
			deleteInterest(ui.tagLabel);
			//alert("Removed");
		},
		
		afterTagAdded : function(event, ui) {
			if (ui.duringInitialization != true) {
				saveInterest(ui.tagLabel);
				//alert("Added");
			}
		},
		caseSensitive : false,
		allowSpaces : true,
		tagLimit : 10 
// 		readOnly: '${ownProfile}' == 'false'
	});
	
	$("#uploadLink").click(
							function(event) {
								event.preventDefault();
								$("#photoFileInput").trigger('click');
							}
						  );
	
	$("#photoFileInput").change(
							function() {
								$("#photoUploadForm").submit();
							}
								);
	
	$("#photoUploadForm").on("submit", uploadPhoto);
	
});

</script>






































