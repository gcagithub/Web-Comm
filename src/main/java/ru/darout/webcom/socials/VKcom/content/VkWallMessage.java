package ru.darout.webcom.socials.VKcom.content;

/**
 * See list vk.com attributes on http://vk.com/dev/wall.post
 *
 */
public class VkWallMessage {
	
	private byte _ownerId;
	private boolean _friendsOnly;
	private byte _fromGroup;
	private String _message;
	private String _attachments;
	
	public VkWallMessage(String message){
		_message = message;
	}
	
	public String getAttachments() {
		return _attachments;
	}
	public void setAttachments(String attachments) {
		this._attachments = attachments;
	}
	public byte getOwnerId() {
		return _ownerId;
	}
	public void setOwnerId(byte ownerId) {
		this._ownerId = ownerId;
	}
	public boolean isFriendsOnly() {
		return _friendsOnly;
	}
	public void setFriendsOnly(boolean friendsOnly) {
		this._friendsOnly = friendsOnly;
	}
	public byte getFromGroup() {
		return _fromGroup;
	}
	public void setFromGroup(byte fromGroup) {
		this._fromGroup = fromGroup;
	}
	public String getMessage() {
		return _message;
	}
	public void setMessage(String message) {
		this._message = message;
	}
	
	@Override
	public String toString() {
		return "VkWallMessage [ownerId=" + _ownerId + ", friendsOnly="
				+ _friendsOnly + ", fromGroup=" + _fromGroup + ", message="
				+ _message + ", attachments=" + _attachments + "]";
	}
	
}
